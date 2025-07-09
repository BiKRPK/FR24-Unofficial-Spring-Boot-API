package example.flight.model;

import org.springframework.data.annotation.Id;

record Aircraft(@Id String callSign, String type, String registration, Operator operator) {
    
}
