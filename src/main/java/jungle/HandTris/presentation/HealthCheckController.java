package jungle.HandTris.presentation;

import jungle.HandTris.global.dto.ResponseEnvelope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/ping")
    public ResponseEnvelope<String> pingpong() {
        return ResponseEnvelope.of("pong");
    }

    @GetMapping("/test")
    public ResponseEnvelope<String> test() {
        return ResponseEnvelope.of("test");
    }
}
