package example.flight.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import example.flight.model.in.AirportTrafficStatsFR24;
import example.flight.model.out.Airport;
import example.flight.model.out.AirportTrafficStats;

@Mapper(componentModel = "spring")
public interface AirportTrafficStatsMapper {

    @Mapping(target = "airport", source = "details", qualifiedByName = "mapAirport")
    @Mapping(target = "flightsToday", source = "details.stats", qualifiedByName = "sumTotals")
    AirportTrafficStats toAirportTrafficStats(AirportTrafficStatsFR24 airportDetailsResponse);

    @Named("mapAirport")
    static Airport mapAirport(AirportTrafficStatsFR24.Details details) {
        return new Airport(
            details.code().iata(),
            details.code().icao(),
            details.name(),
            details.position().latitude(),
            details.position().longitude()
        );
    }

    @Named("sumTotals")
    static int sumTotals(AirportTrafficStatsFR24.Details.Stats stats) {
        return stats.arrivals().total() + stats.departures().total();
    }
}
