package example.flight.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import example.flight.client.AirportClient;
import example.flight.mapper.AirportTrafficStatsMapper;
import example.flight.mapper.MostTrafficAirportMapper;
import example.flight.model.out.AirportTrafficStats;
import reactor.core.publisher.Mono;

@Service
public class AirportService {
 
    @Value("${fr24.base-urls.web}")
    private String webBaseUrl;

    private final AirportClient airportClient;
    private final MostTrafficAirportMapper mostTrafficAirportMapper;
    private final AirportTrafficStatsMapper airportTrafficStatsMapper;

    public AirportService(
            AirportClient airportClient,
            MostTrafficAirportMapper mostTrafficAirportMapper,
            AirportTrafficStatsMapper airportTrafficStatsMapper
    ) {
        this.airportClient = airportClient;
        this.mostTrafficAirportMapper = mostTrafficAirportMapper;
        this.airportTrafficStatsMapper = airportTrafficStatsMapper;
    }

    public Mono<List<AirportTrafficStats>> getMostTrafficAirports(int limit) {
        return airportClient.getMostTrafficAirportsFR24(limit)
                 .map(list -> list.stream()
                    .map(mostTrafficAirportMapper::toAirportStats)
                    .toList());
    }

    public Mono<AirportTrafficStats> getAirportTraffic(String airportIata) {
        return airportClient.getAirportTrafficFR24(airportIata)
                .map(airportTrafficStatsMapper::toAirportTrafficStats);
    }
    
}
