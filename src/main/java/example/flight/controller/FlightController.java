package example.flight.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import example.flight.config.Countries;
import example.flight.model.geo.Bounds;
import example.flight.model.geo.Circle;
import example.flight.model.geo.IsoCountryCode;
import example.flight.model.out.Flight;
import example.flight.model.out.TrackedFlight;
import example.flight.service.FlightService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/live-flights")
public class FlightController {

    private final FlightService flightService;
    private final Countries zones;

    public FlightController(
            FlightService  flightService,
            Countries zones
    ) {
         this.flightService = flightService;
         this.zones = zones;
    }

    @GetMapping("/by-bounds")
    public Mono<List<Flight>> getFlightsByBounds(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam double north,
            @RequestParam double south,
            @RequestParam double west,
            @RequestParam double east
    ) {
        Bounds bounds = new Bounds(north, south, west, east);
        return flightService.getFlights(bounds, limit);
    }

    @GetMapping("/by-radius")
    public Mono<List<Flight>> getFlightsByRadius(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radiusInKm
    ) {
        Circle circle = new Circle(latitude, longitude, radiusInKm);
        return flightService.getFlights(circle, limit);
    }

    @GetMapping("/{country}")
    public Mono<List<Flight>> getFlightsByCountry(
            @PathVariable String country,
            @RequestParam(required = false, defaultValue = "100") int limit
    ) {
        IsoCountryCode isoCountryCode;
        try {
            isoCountryCode = IsoCountryCode.valueOf(country.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Mono.error(
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsuported ISO 3166-1 alpha-2 code: " + country + " - must be one of " + Arrays.toString(IsoCountryCode.values())
                )
            );
        }

        Bounds countryBounds = zones.getBoundsForCountry(isoCountryCode);
        return flightService.getFlights(countryBounds, limit);
    }

    @GetMapping("/most-tracked")
    public Mono<List<TrackedFlight>> getMostTrackedFlights() {
        return flightService.getMostTrackedFlights();
    }

    //TO DO: improve error handling logic
    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
            Map<String, Object> errorAttributes = new HashMap<>();
            errorAttributes.put("status", ex.getStatusCode().value());
            errorAttributes.put("error", ex.getReason());
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(errorAttributes);
        }
    }
    
}
