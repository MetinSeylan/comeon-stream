package comeon.stream.configurations;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EventStream {
    String ON_RESOURCE_INPUT = "onResourceUrlInput";

    @Input("ON_EMAIL")
    KStream<?, ?> onEmailInput();

    @Output
    MessageChannel onEmailOutput();

    @Input
    SubscribableChannel onResourceUrlInput();

    @Output
    MessageChannel onResourceUrlOutput();
}
