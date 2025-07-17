package example.flight.config;

import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.flight.model.geo.Bounds;
import example.flight.model.geo.CountryData;
import example.flight.model.geo.IsoCountryCode;
import jakarta.annotation.PostConstruct;

@Component
public class Countries {
    private Map<String, CountryData> countries;

    @PostConstruct
    public void load() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        countries = mapper.readValue(
            new ClassPathResource("countries.json").getInputStream(),
            new TypeReference<Map<String, CountryData>>() {}
        );
    }

    public Map<String, CountryData> getCountries() {
        return countries;
    }

    public Bounds getBoundsForCountry(IsoCountryCode code) {
        CountryData data = countries.get(code.name());
        return (data != null) ? data.bounds() : null;
    }

    public CountryData getData(IsoCountryCode code) {
        return countries.get(code.name());
    }
}
