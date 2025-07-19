package example.flight.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import example.flight.model.out.AirportTrafficStats;
import example.flight.service.AirportService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController( AirportService  airportService ) {
        this.airportService = airportService;
    }

    @GetMapping("/top-traffic")
    public Mono<List<AirportTrafficStats>> getMostTrafficAirports(
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        return airportService.getMostTrafficAirports(limit);
    }

    @GetMapping("/{airportIata}/traffic")
    public Mono<AirportTrafficStats> getAirportTraffic(@PathVariable String airportIata) {
        return airportService.getAirportTraffic(airportIata);
    }
    
}
