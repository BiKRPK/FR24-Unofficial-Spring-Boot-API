package example.flight.model.geo;

import example.flight.model.out.Airport;
import example.flight.model.out.Flight;

public record Circle (double latitude, double longitude, double radiusInKm) {

    public Bounds toBounds() {
        double north = Math.round((latitude + (radiusInKm / 111.32)) * 100.0) / 100.0;
        double south = Math.round((latitude - (radiusInKm / 111.32)) * 100.0) / 100.0;
        double west = Math.round((longitude - (radiusInKm / (111.32 * Math.cos(Math.toRadians(latitude))))) * 100.0) / 100.0;
        double east = Math.round((longitude + (radiusInKm / (111.32 * Math.cos(Math.toRadians(latitude))))) * 100.0) / 100.0;

        return new Bounds(north, south, west, east);
    }

    public boolean isFlightInCircle(Flight flight) {
        double distance = calculateDistance(flight);
        return distance <= radiusInKm;
    }

    public double calculateDistance(double lat, double lon) {
        final double EARTH_RADIUS = 6371.0;

        double lat1Rad = Math.toRadians(latitude);
        double lat2Rad = Math.toRadians(lat);
        double lon1Rad = Math.toRadians(longitude);
        double lon2Rad = Math.toRadians(lon);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.round(Math.sqrt(x * x + y * y) * EARTH_RADIUS * 100.0) / 100.0;

        return distance;
    }

    public double calculateDistance(Flight flight) {
       return calculateDistance(flight.getLatitude(), flight.getLongitude());
    }

    public double calculateDistance(Airport airport) {
       return calculateDistance(airport.getLatitude(), airport.getLongitude());
    }
}

