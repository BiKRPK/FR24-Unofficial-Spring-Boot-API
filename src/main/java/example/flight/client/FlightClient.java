package example.flight.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import example.flight.config.HeadersProperties;
import example.flight.model.geo.Bounds;
import reactor.core.publisher.Mono;


@Component
public class FlightClient {

    @Value("${fr24.base-urls.api}")
    private String baseUrlApi;

    @Value("${fr24.base-urls.cdn}")
    private String baseUrlCdn;

    @Value("${fr24.base-urls.web}")
    private String baseUrlWeb;

    @Value("${fr24.base-urls.data-live}")
    private String baseUrlLive;

    @Value("${fr24.base-urls.data-cloud}")
    private String baseUrlCloud;
    
    private final WebClient webClient;
    private final HeadersProperties headersProperties;


    public FlightClient(WebClient.Builder builder, HeadersProperties headersProperties) {
        this.webClient = builder.filter((request, next) -> {
            System.out.println("Request URL: " + request.url());
            return next.exchange(request);
        }).build();
        this.headersProperties = headersProperties;

    }

    public Mono<String> getFlights(Bounds bounds) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(baseUrlCloud)
                .path("/zones/fcgi/feed.js")
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
                .queryParam("limit", "100")
                .build())
            .headers(httpHeaders -> httpHeaders.setAll(headersProperties.getDefault()))
            .retrieve()
            .bodyToMono(String.class);
    }
}
