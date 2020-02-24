package love.lipbcu.tacocloud.integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "love.lipbcu.tacocloud.api")
@Component
public class ApiProperties {
    private String url;
}
