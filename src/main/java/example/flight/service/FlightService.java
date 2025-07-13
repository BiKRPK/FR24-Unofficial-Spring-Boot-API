package example.flight.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import example.flight.client.FlightClient;
import example.flight.model.geo.Bounds;
import example.flight.model.out.Flight;
import example.flight.model.out.TrackedFlight;
import reactor.core.publisher.Mono;

@Service
public class FlightService {
 
    @Value("${fr24.base-urls.web}")
    private String webBaseUrl;

    private final FlightClient flightClient;

    public FlightService(FlightClient flightClient) {
        this.flightClient = flightClient;
    }

    public Mono<List<Flight>> getFlights(Bounds bounds, int limit) {
        return flightClient.getFlightsFR24(bounds, limit)
            .map(this::parseFlights);
    }

    private List<Flight> parseFlights(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = JsonNodeFactory.instance.objectNode();
        try {
            root =  mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Flight> flights = new ArrayList<>();

        Set<Map.Entry<String, JsonNode>> fields = root.properties();

        for (Map.Entry<String, JsonNode> entry : fields) {
            if (entry.getValue().isArray()) {
                JsonNode arr = entry.getValue();

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
                        URI.create(webBaseUrl + "/" + callsign)
                );
                flights.add(flight);
            }
        }
        return flights;
    }

    public Mono<List<TrackedFlight>> getMostTrackedFlights() {
        return flightClient.getMostTrackedFlightsFR24()
            .map(this::parseTrackedFlights);
    }

    private List<TrackedFlight> parseTrackedFlights(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = JsonNodeFactory.instance.objectNode();
        try {
            root =  mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode data = root.get("data");
        List<TrackedFlight> flights = new ArrayList<>();

        int position = 1;
        for (JsonNode flightNode : data) {
            
            String callsign = flightNode.path("callsign").asText().trim();
            int trackers = flightNode.path("clicks").asInt();
            String model = flightNode.path("model").asText();
            String fromIata = flightNode.path("from_iata").asText();
            String toIata = flightNode.path("to_iata").asText();

            Flight flight = new Flight(
                callsign,
                0.0,
                0.0,
                model,
                fromIata,
                toIata,
                URI.create(webBaseUrl + "/" + callsign)
            );

            TrackedFlight trackedFlight = new TrackedFlight(flight, position, trackers);
            flights.add(trackedFlight);
        }
        return flights;
    }

}
