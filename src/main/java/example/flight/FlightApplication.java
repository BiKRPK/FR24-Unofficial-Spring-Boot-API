package example.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import example.flight.config.StaticZonesProperties;

@SpringBootApplication
@EnableConfigurationProperties(StaticZonesProperties.class)
public class FlightApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightApplication.class, args);
	}

}
