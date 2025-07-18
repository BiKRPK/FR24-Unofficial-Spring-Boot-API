package example.flight.model.in;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MostTrackedFlightsFR24(
        List<TrackedFlightFR24> data,
        @JsonProperty("update_time")
        long updateTime,
        String version
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TrackedFlightFR24(
        String callsign,
        int clicks,
        String flight,
        @JsonProperty("flight_id")
        String flightId,
        @JsonProperty("from_city")
        String fromCity,
        @JsonProperty("from_iata")
        String fromIata,
        String model,
        String squawk,
        @JsonProperty("to_city")
        String toCity,
        @JsonProperty("to_iata")
        String toIata,
        String type
    ) {}
}
