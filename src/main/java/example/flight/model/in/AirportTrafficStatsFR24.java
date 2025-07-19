package example.flight.model.in;

import java.util.List;
import java.util.Map;

public record AirportTrafficStatsFR24(
    Details details
) {
    public record Details(
        String name,
        Code code,
        Position position,
        Timezone timezone,
        boolean visible,
        String website,
        Stats stats
    ) {
        public record Code(
            String iata,
            String icao
        ) {}
        public record Position(
            double latitude,
            double longitude,
            int altitude,
            Country country,
            Region region
        ) {
            public record Country(
                int id,
                String name,
                String code,
                String codeLong
            ) {}
            public record Region(
                String city
            ) {}
        }
        public record Stats(
            FlightStats arrivals,
            FlightStats departures
        ) {
            public record FlightStats(
                Double delayIndex,
                Double delayAvg,
                int  total,
                Map<String, String> hourly,
                List<String> stats
            ) {}
        }
    }
    public record Timezone(
        String name,
        int offset,
        String offsetHours,
        String abbr,
        String abbrName,
        boolean isDst
    ) {}
}
