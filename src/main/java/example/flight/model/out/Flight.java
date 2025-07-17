package example.flight.model.out;

import java.net.URI;

//record Flight(@Id String id, String label, double latitude, double longitude, Aircraft aircraft, Airport origin, Airport destination, Date etd, Date eta, URI liveURL) {
public class Flight {
    private String callSign;
    private double latitude;
    private double longitude;
    private String aircraft;
    private String origin;
    private String destination;
    private URI liveURL;

    public Flight() {}

    public Flight(String callSign, double latitude, double longitude, String aircraft, String origin, String destination, URI liveURL) {
        this.callSign = callSign;
        this.latitude = latitude;
        this.longitude = longitude;
        this.aircraft = aircraft;
        this.origin = origin;
        this.destination = destination;
        this.liveURL = liveURL;
    }


    public String getCallSign() {
        return callSign;
    }
    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAircraft() {
        return aircraft;
    }
    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public URI getLiveURL() {
        return liveURL;
    }
    public void setLiveURL(URI liveURL) {
        this.liveURL = liveURL;
    }
}
