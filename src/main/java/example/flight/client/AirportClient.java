package example.flight.client;

import java.util.Arrays;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import example.flight.config.BaseUrlProperties;
import example.flight.config.EndpointProperties;
import example.flight.config.HeadersProperties;
import example.flight.model.in.AirportTrafficStatsFR24;
import example.flight.model.in.MostTrackedFlightsFR24;
import example.flight.model.in.MostTrafficAirportFR24;
import reactor.core.publisher.Mono;

@Component
public class AirportClient {
    private final WebClient webClient;
    private final BaseUrlProperties baseUrlProperties;
    private final EndpointProperties endpointProperties;
    private final HeadersProperties headersProperties;
    
    public AirportClient(
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

    public Mono<List<MostTrafficAirportFR24>> getMostTrafficAirportsFR24(int limit) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(baseUrlProperties.web())
                .path(endpointProperties.airportData())
                .queryParam("limit", limit)
                .build())
            .headers(httpHeaders -> httpHeaders.setAll(headersProperties.getDefault()))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<MostTrafficAirportFR24>>() {});
    }

    public Mono<AirportTrafficStatsFR24> getAirportTrafficFR24(String airportIata) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(baseUrlProperties.web())
                .path(endpointProperties.airportData())
                .queryParam("airport", airportIata)
                .queryParam("limit", 1)
                .build())
            .headers(httpHeaders -> httpHeaders.setAll(headersProperties.getDefault()))
            .retrieve()
            .bodyToMono(AirportTrafficStatsFR24.class);
    }
}
