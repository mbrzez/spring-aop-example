package pl.brzezins.logs.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.brzezins.logs.domain.model.LogMessage;
import pl.brzezins.logs.domain.ports.LogStreamer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest("spring.main.allow-bean-definition-overriding=true")
class LogMonitoringAspectTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LogStreamer logStreamer;

    /*@Slf4j
    @TestConfiguration
    public static class LogAspectTestConfig {
        @Bean
        public LogStreamer logStreamer() {
           return (message) -> log.info(message.toString());
        }
    }*/

    @Test
    void should_check_if_aspect_works_for_get() throws Exception {
        mockMvc.perform(get("/test/get"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(logStreamer, times(1)).send(any());
    }

    @Test
    void should_check_if_aspect_for_post() throws Exception {
        var request = new LogController.Request("A message");

        mockMvc.perform(post("/test/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(logStreamer, times(2)).send(any());
    }

    @Test
    void should_check_if_aspect_for_post_request() throws Exception {
        var request = new LogController.Request("A message");

        mockMvc.perform(post("/test/post/request")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        var logMessage = new LogMessage("{\"message\":\"A message\"}");
        verify(logStreamer, times(1)).send(logMessage);
    }

    @Test
    void should_check_if_aspect_for_post_response() throws Exception {
        var request = new LogController.Request("A message");

        mockMvc.perform(post("/test/post/response")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        var logMessage = new LogMessage("{\"message\":\"A response\"}");
        verify(logStreamer, times(1)).send(logMessage);
    }

    @Test
    void should_check_if_aspect_for_post_when_exception() throws Exception {
        var request = new LogController.Request("A message");

        mockMvc.perform(post("/test/throw")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        verify(logStreamer, times(1)).send(any());
    }
}