package comeon.stream.controllers.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateTaskRequest {
    @NotEmpty
    private List<String> emails = new ArrayList<>();
    @NotEmpty
    private List<String> urls = new ArrayList<>();
}
