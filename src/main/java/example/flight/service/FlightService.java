package example.flight.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import example.flight.client.FlightClient;
import example.flight.mapper.LiveFlightMapper;
import example.flight.mapper.TrackedFlightMapper;
import example.flight.model.geo.Bounds;
import example.flight.model.geo.Circle;
import example.flight.model.out.Flight;
import example.flight.model.out.TrackedFlight;
import reactor.core.publisher.Mono;

@Service
public class FlightService {
 
    @Value("${fr24.base-urls.web}")
    private String webBaseUrl;

    private final FlightClient flightClient;
    private final TrackedFlightMapper trackedFlightMapper;
    private final LiveFlightMapper liveFlightMapper;

    public FlightService(
            FlightClient flightClient,
            TrackedFlightMapper trackedFlightMapper,
            LiveFlightMapper liveFlightMapper
    ) {
        this.flightClient = flightClient;
        this.trackedFlightMapper = trackedFlightMapper;
        this.liveFlightMapper = liveFlightMapper;
    }

    public Mono<List<Flight>> getFlights(Bounds bounds, int limit) {
        return flightClient.getFlightsFR24(bounds, limit)
                .map(liveFlightsFR24 -> liveFlightMapper.toFlight(liveFlightsFR24));
    }

    public Mono<List<Flight>> getFlights(Circle circle, int limit) {
        return flightClient.getFlightsFR24(circle.toBounds(), limit * 2)
                .map(liveFlightsFR24 -> liveFlightMapper.toFlight(liveFlightsFR24))
                .map(flights -> flights.stream()
                    .filter(circle::isFlightInCircle)
                    .sorted(Comparator.comparingDouble(circle::calculateDistance))
                    .limit(limit)
                    .toList());
    }

    public Mono<List<TrackedFlight>> getMostTrackedFlights() {
        return flightClient.getMostTrackedFlightsFR24()
                .map(mostTracked -> trackedFlightMapper.toTrackedFlightList(mostTracked, webBaseUrl));
    }
    
}
