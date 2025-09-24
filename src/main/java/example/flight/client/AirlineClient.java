package example.flight.client;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import example.flight.config.BaseUrlProperties;
import example.flight.config.EndpointProperties;
import example.flight.config.HeadersProperties;
import example.flight.model.out.Airline;
import reactor.core.publisher.Mono;

@Component
public class AirlineClient {
    private final WebClient webClient;
    private final BaseUrlProperties baseUrlProperties;
    private final EndpointProperties endpointProperties;
    private final HeadersProperties headersProperties;
    
    public AirlineClient(
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

    public Mono<List<Airline>> getAllAirlinesFR24() {
        return Mono.empty();
    }

}
