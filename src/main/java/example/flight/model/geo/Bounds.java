package example.flight.model.geo;

public record Bounds(double latitudeNorth, double longitudeWest, double latitudeSouth,  double longitudeEast)  {

    public String toQueryParam() {
        return latitudeNorth + "," + latitudeSouth + "," + longitudeWest + "," + longitudeEast;
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "latitudeNorth=" + latitudeNorth +
                ", latitudeSouth=" + latitudeSouth +
                ", longitudeWest=" + longitudeWest +
                ", longitudeEast=" + longitudeEast +
                '}';
    }
}
