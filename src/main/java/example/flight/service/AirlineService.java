package example.flight.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import example.flight.model.out.Airline;
import reactor.core.publisher.Mono;

@Service
public class AirlineService {
 
    @Value("${fr24.base-urls.web}")
    private String webBaseUrl;

    private final AirlineClient airlineClient;

    public AirlineService(
            AirlineClient airlineClient
    ) {
        this.airlineClient = airlineClient;
    }

    // public Mono<List<Airline>> getAllAirlines() {
    //     return airlineClient.getAllAirlinesFR24();
    // }
    
}
