package comeon.stream.services;

import comeon.stream.configurations.EventStream;
import comeon.stream.controllers.requests.CreateTaskRequest;
import comeon.stream.services.models.OnEmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskService {
    private final EventStream outputEvent;

    public TaskService(EventStream outputEvent){
        this.outputEvent = outputEvent;
    }

    public void create(CreateTaskRequest createTaskRequest) {
        createTaskRequest.getEmails().forEach(this::publishEmail);
        createTaskRequest.getUrls().forEach(this::publishUrl);
    }

    private void publishEmail(String email) {
        log.debug("publishing email {}", email);
        outputEvent.onEmailOutput().send(
                MessageBuilder.withPayload(
                        OnEmailEvent.builder()
                                .email(email)
                                .build()
                ).build()
        );
    }

    public void publishUrl(String url) {
        log.info("publishing url {}", url);
        outputEvent.onResourceUrlOutput().send(
                MessageBuilder.withPayload(url).build()
        );
    }
}
