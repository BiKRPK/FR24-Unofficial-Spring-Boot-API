package example.flight.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import example.flight.config.Zones;
import example.flight.model.geo.Zone;
import example.flight.model.geo.Bounds;
import example.flight.model.out.Flight;
import example.flight.model.out.TrackedFlight;
import example.flight.service.FlightService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/live-flights")
public class FlightController {

    private final FlightService flightService;
    private final Zones zones;

    public FlightController(
            FlightService  flightService,
            Zones zones
    ) {
         this.flightService = flightService;
         this.zones = zones;
    }

    @GetMapping("/by-bounds")
    public Mono<List<Flight>> getFlightsByBounds(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam double maxLatitude,
            @RequestParam double minLatitude,
            @RequestParam double minLongitude,
            @RequestParam double maxLongitude
    ) {
        Bounds bounds = new Bounds(maxLatitude, minLatitude, minLongitude, maxLongitude);
        return flightService.getFlights(bounds, limit);
    }

    @GetMapping("/{country}")
    public Mono<List<Flight>> getFlightsByCountry(
            @PathVariable String country,
            @RequestParam(required = false, defaultValue = "100") int limit
    ) {
        Zone zone = null;
        try {
            zone = Zone.valueOf(country.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Mono.error(
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsuported country: " + country + " - must be one of " + Zone.values()
                )
            );
        }

        Bounds countryBounds = zones.getBoundsForZone(zone);
        return flightService.getFlights(countryBounds, limit);
    }

    @GetMapping("/most-tracked")
    public Mono<List<TrackedFlight>> getMostTrackedFlights() {
        return flightService.getMostTrackedFlights();
    }
    
}
