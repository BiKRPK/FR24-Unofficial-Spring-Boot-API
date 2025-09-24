package example.flight.model.out;
import java.util.List;

public record Airline(String name, String iata, String icao, List<Aircraft> fleet) {

}