package comeon.stream.consumers;

import comeon.stream.configurations.EventStream;
import comeon.stream.services.ResourceService;
import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OnResourceConsumer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OnResourceConsumer.class);
    private final ResourceService resourceService;

    public OnResourceConsumer(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @StreamListener(EventStream.ON_RESOURCE_INPUT)
    public void process(@Payload String resource) {
        log.info("processing url: {}", resource);
        resourceService.processResource(resource);
    }
}
