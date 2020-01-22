package comeon.stream.controllers;

import comeon.stream.controllers.requests.CreateTaskRequest;
import comeon.stream.services.StorageService;
import comeon.stream.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController("api/v1/email")
public class EmailController {
    private final TaskService taskService;
    private final StorageService storageService;

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public void createTask(@Valid CreateTaskRequest createTaskRequest) {
        log.debug("createTask requesting with {} number of email and {} number of url", createTaskRequest.getEmails().size(), createTaskRequest.getUrls().size());
        taskService.create(createTaskRequest);
    }

    @GetMapping
    public void retrieve(@RequestParam("email") String email) {
        storageService.retrieve(email);
    }
}
