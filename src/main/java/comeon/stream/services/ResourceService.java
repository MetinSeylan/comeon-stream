package comeon.stream.services;

import comeon.stream.controllers.requests.CreateTaskRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@AllArgsConstructor
public class ResourceService {
    private final TaskService taskService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public void processResource(String url) {
        fetch(url);
    }

    @Retryable(value = {TimeoutException.class, HttpServerErrorException.class, UnknownHostException.class, ConnectException.class})
    private void fetch(String url) {
        CreateTaskRequest createTaskRequest = restTemplate.getForObject(url, CreateTaskRequest.class);
        taskService.create(createTaskRequest);
    }

    @Recover
    private void recover(Exception e, String url) {
        log.error("an error occurred while fetching url {}, Exception:{}", url, e);
        taskService.publishUrl(url);
    }
}
