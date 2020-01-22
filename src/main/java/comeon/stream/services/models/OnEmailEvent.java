package comeon.stream.services.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class OnEmailEvent {
    @JsonProperty("email")
    private String email;
}
