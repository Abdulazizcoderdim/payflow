package uz.payflow.common;

import java.time.Instant;

public record PingResponse(String service, String status, Instant time) {
}