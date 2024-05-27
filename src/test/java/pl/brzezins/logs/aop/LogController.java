package pl.brzezins.logs.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.brzezins.logs.domain.ports.LogMonitoring;
import pl.brzezins.logs.domain.ports.LogRequestMonitoring;
import pl.brzezins.logs.domain.ports.LogResponseMonitoring;

@Slf4j
@RestController
public class LogController {
    @LogMonitoring
    @GetMapping(value = "/test/get")
    public Response throwingExceptionMapping() {
        return new Response("A response");
    }

    @LogMonitoring
    @PostMapping("/test/post")
    public Response postMapping(@RequestBody Request request) {
        return new Response("A response");
    }

    @LogRequestMonitoring
    @PostMapping("/test/post/request")
    public Response postMappingRequest(@RequestBody Request request) {
        return new Response("A response");
    }

    @LogResponseMonitoring
    @PostMapping("/test/post/response")
    public Response postMappingResponse(@RequestBody Request request) {
        return new Response("A response");
    }

    @LogMonitoring
    @PostMapping(value = "/test/throw")
    public Response throwingExceptionMapping(@RequestBody Request request) {
        throw new RuntimeException("An exception");
    }

    @ControllerAdvice(basePackageClasses = LogController.class)
    public static class LogControllerAdvice {
        @ExceptionHandler(Exception.class)
        public final ResponseEntity<?> handleException(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Request(e.getMessage()));
        }
    }

    public record Request(String message) {};
    public record Response(String message) {};
}
