package example.flight.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fr24.headers")
public class HeadersProperties {
    private Map<String, String> defaultHeaders;

    public Map<String, String> getDefault() {
        return defaultHeaders;
    }

    public void setDefault(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }
}

