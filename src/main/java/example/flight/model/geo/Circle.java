package example.flight.model.geo;

import example.flight.model.out.Flight;

public record Circle (double latitude, double longitude, double radiusinKm) {

    public Bounds toBounds() {
        Bounds bounds = new Bounds(
                latitude + (radiusinKm / 111.32),
                latitude - (radiusinKm / 111.32),
                longitude - (radiusinKm / (111.32 * Math.cos(Math.toRadians(latitude)))),
                longitude + (radiusinKm / (111.32 * Math.cos(Math.toRadians(latitude))))
        );
        return bounds;
    }

    public boolean isFlightInCircle(Flight flight) {
        double distance = calculateDistance(latitude, longitude, flight.getLatitude(), flight.getLongitude());
        return distance <= radiusinKm;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS = 6371.0;

        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * EARTH_RADIUS;

        return distance;
    }
}
