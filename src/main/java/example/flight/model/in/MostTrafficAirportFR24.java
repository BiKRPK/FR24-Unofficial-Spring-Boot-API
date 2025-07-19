package example.flight.model.in;

import java.util.List;

public record MostTrafficAirportFR24(
        Airport airport,
        Flights flights
) {
    public record Airport(
            String name,
            Code code,
            Position position,
            Timezone timezone,
            boolean visible,
            String website
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
    }
    public record Timezone(
            String name,
            int offset,
            String offsetHours,
            String abbr,
            String abbrName,
            boolean isDst
    ) {}
    public record Flights(
            String total,
            List<Hourly> hourly,
            List<Integer> stats
    ) {
        public record Hourly(
                int numFlights,
                String hour
        ) {}
    }
}
