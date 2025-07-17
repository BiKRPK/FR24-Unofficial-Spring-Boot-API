package example.flight.model.in;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;

public class LiveFlightsFR24 {
    private int fullCount;
    private int version;
    private JsonNode stats;
    private Map<String, JsonNode> flights = new HashMap<>();

    @JsonAnySetter
    public void add(String key, JsonNode value) {
        switch (key) {
            case "full_count":
                this.fullCount = value.asInt();
                break;
            case "version":
                this.version = value.asInt();
                break;
            case "stats":
                this.stats = value;
                break;
            default:
                // Solamente guardamos las que claramente son vuelos
                if (value.isArray()) {
                    flights.put(key, value);
                }
        }
    }

    public int getFullCount() { return fullCount; }
    public int getVersion() { return version; }
    public JsonNode getStats() { return stats; }
    public Map<String, JsonNode> getFlights() { return flights; }
}
