package example.flight.model.out;

public record Airport(String iata, String icao, String name, double latitude, double longitude) {
    
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
