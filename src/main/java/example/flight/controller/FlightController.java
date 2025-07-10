package example.flight.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.flight.client.FlightClient;
import example.flight.config.StaticZonesProperties;
import example.flight.model.geo.Bounds;
import example.flight.model.out.Flight;
import example.flight.service.FlightService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/live-flights")
public class FlightController {

    private final FlightClient flightClient;
    private final FlightService flightService;
    private final StaticZonesProperties staticZonesProperties;

    public FlightController(FlightClient flightClient, FlightService  flightService, StaticZonesProperties staticZonesProperties) {
         this.flightClient = flightClient;
         this.flightService = flightService;
         this.staticZonesProperties = staticZonesProperties;
    }

    @GetMapping("/by-bounds")
    public Mono<List<Flight>> getFlightsByBounds(
            @RequestParam double north,
            @RequestParam double south,
            @RequestParam double west,
            @RequestParam double east
    ) {
        Bounds bounds = new Bounds(north, south, west, east);
        return flightClient.getFlights(bounds).map(flightService::parseFlights);
    }

    @GetMapping("/spain")
    public Mono<List<Flight>> getFlightsInSpain() {
        //Bounds spainBounds = staticZonesProperties.getBoundsForZone(Zones.SPAIN);
        Bounds spainBounds = new Bounds(44.36, -11.06, 35.76, 4.04);
        return flightClient.getFlights(spainBounds)
                .map(flightService::parseFlights);
    }
    
}
