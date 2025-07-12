package example.flight.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fr24.base-urls")
public record BaseUrlProperties(
    String api,
    String cdn,
    String web,
    String dataLive,
    String dataCloud
) {}
