package example.flight.model.geo;

public record Bounds(double north, double south, double west,  double east)  {

    public String toQueryParam() {
        return north + "," + south + "," + west + "," + east;
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "north=" + north +
                ", south=" + south +
                ", west=" + west +
                ", east=" + east +
                '}';
    }
}