package example.flight.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import example.flight.model.geo.Bounds;
import example.flight.model.geo.Zones;
import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "static-zones")
public class StaticZonesProperties {

    private Map<Zones, Bounds> boundsByZone = new HashMap<>();

    public Map<Zones, Bounds> getZones() {
        return boundsByZone ;
    }

    public void setZones(Map<Zones, Bounds> boundsByZone) {
        this.boundsByZone  = boundsByZone ;
    }

    public Bounds getBoundsForZone(Zones zone) {
        return boundsByZone.get(zone);
    }

    @PostConstruct
    public void logLoadedZones() {
        System.out.println("=== Zonas cargadas desde YAML ===");
        System.out.println(Zones.SPAIN);
        if (boundsByZone == null || boundsByZone.isEmpty()) {
            System.out.println("No se han cargado zonas. Verifica la configuración.");
        } else {
            boundsByZone.forEach((zone, bounds) -> {
                System.out.println("Zone: " + zone + " -> " + bounds);
            });
        }
        System.out.println("=== Fin de zonas cargadas ===");
    }
}
