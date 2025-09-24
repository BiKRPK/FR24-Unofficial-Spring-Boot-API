package example.flight.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.flight.model.out.Airline;
import example.flight.service.AirlineService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController( AirlineService  airlineService ) {
        this.airlineService = airlineService;
    }

    @GetMapping("")
    public Mono<List<Airline>> getAllAirlines(
            //@RequestParam(required = false) int limit
    ) {
        return airlineService.getAllAirlines();
    }

    
}
