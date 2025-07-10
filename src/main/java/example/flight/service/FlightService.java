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

import example.flight.model.out.Flight;

@Service
public class FlightService {
 
    @Value("${fr24.base-urls.web}")
    private String webBaseUrl;

    public List<Flight> parseFlights(String json) {
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
                Flight flight = new Flight(
                        arr.get(16).asText(),
                        arr.get(1).asDouble(),
                        arr.get(2).asDouble(),
                        arr.get(8).asText(),
                        arr.get(11).asText(),
                        arr.get(12).asText(),
                        URI.create(webBaseUrl + "/" + arr.get(16).asText())
                );
                flights.add(flight);
            }
        }
        return flights;
    }

}
