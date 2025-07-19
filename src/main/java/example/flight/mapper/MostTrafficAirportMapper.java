package example.flight.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import example.flight.model.in.MostTrafficAirportFR24;
import example.flight.model.out.Airport;
import example.flight.model.out.AirportTrafficStats;

@Mapper(componentModel = "spring")
public interface MostTrafficAirportMapper {

    @Mapping(target = "airport", source = "trafficStatsAirportFR24", qualifiedByName = "mapAirport")
    @Mapping(target = "flightsToday", source = "trafficStatsAirportFR24.flights.total", qualifiedByName = "parseTotal")
    AirportTrafficStats toAirportStats(MostTrafficAirportFR24 trafficStatsAirportFR24);

    @Named("mapAirport")
    default Airport mapAirport(MostTrafficAirportFR24 trafficStatsAirportFR24) {
        var sourceAirport = trafficStatsAirportFR24.airport();
        return new Airport(
            sourceAirport.code().iata(),
            sourceAirport.code().icao(),
            sourceAirport.name(),
            sourceAirport.position().latitude(),
            sourceAirport.position().longitude()
        );
    }

    @Named("parseTotal")
    default int parseTotal(String total) {
        try {
            return Integer.parseInt(total);
        } catch (Exception e) {
            return 0;
        }
    }
}
