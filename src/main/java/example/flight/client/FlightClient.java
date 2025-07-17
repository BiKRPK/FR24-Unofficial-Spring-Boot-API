package example.flight.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import example.flight.config.BaseUrlProperties;
import example.flight.config.EndpointProperties;
import example.flight.config.HeadersProperties;
import example.flight.model.geo.Bounds;
import example.flight.model.in.LiveFlightsFR24;
import example.flight.model.in.MostTrackedFR24;
import reactor.core.publisher.Mono;

@Component
public class FlightClient {    
    private final WebClient webClient;
    private final BaseUrlProperties baseUrlProperties;
    private final EndpointProperties endpointProperties;
    private final HeadersProperties headersProperties;
    
    public FlightClient(
            WebClient.Builder builder,
            BaseUrlProperties baseUrlProperties,
            EndpointProperties endpointProperties,
            HeadersProperties headersProperties
    ) {
        this.webClient = builder.filter((request, next) -> {
            System.out.println("Request URL: " + request.url());
            return next.exchange(request);
        }).build();
        this.baseUrlProperties = baseUrlProperties;
        this.endpointProperties = endpointProperties;
        this.headersProperties = headersProperties;
    }

    public Mono<LiveFlightsFR24> getFlightsFR24(Bounds bounds, int limit) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(baseUrlProperties.dataCloud())
                .path(endpointProperties.realTimeFlightTrackerData())
                .queryParam("bounds", bounds.toQueryParam())
                .queryParam("faa", "1")
                .queryParam("satellite", "1")
                .queryParam("mlat", "1")
                .queryParam("flarm", "1")
                .queryParam("adsb", "1")
                .queryParam("gnd", "1")
                .queryParam("air", "1")
                .queryParam("vehicles", "1")
                .queryParam("estimated", "1")
                .queryParam("maxage", "14400")
                .queryParam("gliders", "1")
                .queryParam("stats", "1")
                .queryParam("limit", limit)
                .build())
            .headers(httpHeaders -> httpHeaders.setAll(headersProperties.getDefault()))
            .retrieve()
            .bodyToMono(LiveFlightsFR24.class);
    }

    public Mono<MostTrackedFR24> getMostTrackedFlightsFR24() {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(baseUrlProperties.web())
                .path(endpointProperties.mostTracked())
                .build())
            .headers(httpHeaders -> httpHeaders.setAll(headersProperties.getDefault()))
            .retrieve()
            .bodyToMono(MostTrackedFR24.class);
    }
}
