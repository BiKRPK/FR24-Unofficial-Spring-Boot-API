package example.flight.model.geo;

public record Bounds(double maxLatitude, double minLatitude, double minLongitude,  double maxLongitude)  {

    public String toQueryParam() {
        return maxLatitude + "," + minLatitude + "," + minLongitude + "," + maxLongitude;
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "maxLatitude=" + maxLatitude +
                ", minLatitude=" + minLatitude +
                ", minLongitude=" + minLongitude +
                ", maxLongitude=" + maxLongitude +
                '}';
    }
}