package comeon.stream.services.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class EmailCount {
    private String email;
    private Long count;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
