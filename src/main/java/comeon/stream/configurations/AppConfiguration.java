package comeon.stream.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {
    private Long windowSize;
    private List<String> whiteList;
}
