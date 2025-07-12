package example.flight.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fr24.endpoints")
public record EndpointProperties(
    String userLogin,
    String userLogout,
    String searchData,
    String realTimeFlightTrackerData,
    String flightData,
    String historicalData,
    String apiAirportData,
    String airportData,
    String airportsData,
    String airlinesData,
    String volcanicEruptionData,
    String mostTracked,
    String airportDisruptions,
    String bookmarks,
    String countryFlag,
    String airlineLogo,
    String alternativeAirlineLogo
) {}
