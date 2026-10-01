package uz.payflow.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1")
public class PingController {

    @GetMapping("/ping")
    public PingResponse ping() {
        return new PingResponse("payflow", "UP", Instant.now());
    }
}
