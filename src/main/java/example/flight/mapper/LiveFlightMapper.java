package example.flight.mapper;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import example.flight.model.in.LiveFlightsFR24;
import example.flight.model.out.Flight;

@Component
public class LiveFlightMapper {
    
    @Value("${fr24.base-urls.web}")
    private String webBaseUrl;

    public LiveFlightMapper() {
    }

    public List<Flight> toFlight(LiveFlightsFR24 liveFlightsFR24) {
        List<Flight> flights = new ArrayList<>();
        for (Map.Entry<String, JsonNode> entry : liveFlightsFR24.getFlights().entrySet()) {
            String flightId = entry.getKey();
            JsonNode arr = entry.getValue();
            
            if (arr.isArray() && arr.size() > 16) {
                String callsign = arr.get(16).asText();
                Double latitude = arr.get(1).asDouble();
                Double longitude = arr.get(2).asDouble();
                String model = arr.get(8).asText();
                String fromIata = arr.get(11).asText();
                String toIata = arr.get(12).asText();

                Flight flight = new Flight(
                        callsign,
                        latitude,
                        longitude,
                        model,
                        fromIata,
                        toIata,
                        URI.create(webBaseUrl + "/" + flightId)
                );
                flights.add(flight);
            }
        }
        return flights;
    }
}
