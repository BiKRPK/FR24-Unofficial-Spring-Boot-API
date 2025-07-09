package example.flight;

import org.springframework.data.annotation.Id;

record Airport(@Id String code, String name, double latitude, double longitude) {
    
}
