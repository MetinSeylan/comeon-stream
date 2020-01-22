package comeon.stream.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import comeon.stream.configurations.AppConfiguration;
import comeon.stream.services.StorageService;
import comeon.stream.services.models.EmailCount;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@EnableKafkaStreams
@AllArgsConstructor
public class OnEmailConsumer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AppConfiguration appConfiguration;
    private final StorageService storageService;

    @Bean
    public KStream<Windowed<Object>, Long> process(StreamsBuilder kStreamBuilder) {
        KStream<Windowed<Object>, Long> stream = kStreamBuilder.stream("ON_EMAIL");
        stream
                .map((Object key, Object val) -> parseJson(key, (byte[])val))
                .filter((key, val) -> appConfiguration.getWhiteList().stream().anyMatch(key::contains))
                .groupByKey(Serialized.with(Serdes.String(), Serdes.Long()))
                .windowedBy(TimeWindows.of(appConfiguration.getWindowSize()))
                .count(Materialized.as("windowed-5m-count"))
                .toStream()
                .foreach(this::savePeriod);
        return stream;
    }

    private KeyValue<String, Long> parseJson(Object key, byte[] val) {
        JsonNode onEmailEvent = null;
        try {
            onEmailEvent = objectMapper.readValue(new String(val, StandardCharsets.UTF_8), JsonNode.class);
        } catch (IOException e) {
            log.error("parse error");
        }
        return new KeyValue<String, Long>(onEmailEvent.get("email").asText(), 0L);
    }

    public void savePeriod(Windowed<String> key, Long value) {
        storageService.create(
                EmailCount.builder()
                        .count(value)
                        .email(key.key())
                        .endDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(key.window().end()), ZoneId.systemDefault()))
                        .startDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(key.window().start()), ZoneId.systemDefault()))
                        .build()
        );
    }
}
