package comeon.stream.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class EmailWindowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    private Long id;

    @Email
    private String email;

    private Long count;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
