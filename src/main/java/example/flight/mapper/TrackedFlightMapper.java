package example.flight.mapper;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import example.flight.model.in.MostTrackedFR24;
import example.flight.model.out.Flight;
import example.flight.model.out.TrackedFlight;

@Mapper(componentModel = "spring")
public abstract class TrackedFlightMapper {

    @Mapping(target = "callSign",  expression = "java(source.callsign() != null ? source.callsign().trim() : null)")
    @Mapping(target = "aircraft", source = "model")
    @Mapping(target = "origin", source = "fromIata")
    @Mapping(target = "destination", source = "toIata")
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "liveURL", ignore = true)
    public abstract Flight toFlight(MostTrackedFR24.TrackedFlightFR24 source);


    public TrackedFlight toTrackedFlight(MostTrackedFR24.TrackedFlightFR24 source, int position, String webBaseUrl) {
        Flight flight = toFlight(source);
        if (source.flightId() != null) {
            flight.setLiveURL(URI.create(webBaseUrl  + "/" + source.flightId()));
        }
        return new TrackedFlight(flight, position, source.clicks());
    }

    public List<TrackedFlight> toTrackedFlightList(MostTrackedFR24 mostTracked, String webBaseUrl) {
        List<TrackedFlight> trackedFlights = new ArrayList<>();
        List<MostTrackedFR24.TrackedFlightFR24> data = mostTracked.data();
        for (int i = 0; i < data.size(); i++) {
            trackedFlights.add(toTrackedFlight(data.get(i), i + 1, webBaseUrl));
        }
        return trackedFlights;
    }
}