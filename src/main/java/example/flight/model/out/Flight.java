package example.flight.model.out;

import java.net.URI;

//record Flight(@Id String id, String label, double latitude, double longitude, Aircraft aircraft, Airport origin, Airport destination, Date etd, Date eta, URI liveURL) {
public record Flight(
        String callSign,
        double latitude,
        double longitude,
        String aircraft,
        String origin,
        String destination,
        URI liveURL
) {
    
}