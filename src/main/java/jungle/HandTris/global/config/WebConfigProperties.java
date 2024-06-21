package jungle.HandTris.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "web")
public record WebConfigProperties(Cors cors) {

    public record Cors(
            List<String> allowedOrigins,
            List<String> allowedMethods,
            List<String> allowedHeaders,
            boolean allowCredentials
    ) {}
}
