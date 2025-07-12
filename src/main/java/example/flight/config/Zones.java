package example.flight.config;

import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.flight.model.geo.Bounds;
import example.flight.model.geo.Zone;

@Component
public class Zones {
    private Map<String, Bounds> zones;

    @PostConstruct
    public void load() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        zones = mapper.readValue(
            new ClassPathResource("zones.json").getInputStream(),
            new TypeReference<Map<String, Bounds>>() {}
        );
        System.out.println("Zonas cargadas: " + zones);
    }

    public Map<String, Bounds> getZones() {
        return zones;
    }

    public Bounds getBoundsForZone(Zone zone) {
        return zones.get(zone.name().toLowerCase());
    }
}