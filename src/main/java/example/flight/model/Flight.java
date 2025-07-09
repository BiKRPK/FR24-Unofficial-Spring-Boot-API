package example.flight;

import java.net.URI;
import java.util.Date;

import org.springframework.data.annotation.Id;

record Flight(@Id String id, String label, double latitude, double longitude, Aircraft aircraft, Airport origin, Airport destination, Date etd, Date eta, URI liveURL) {
    
}
