
import article.controllers.MainPageController;
import article.service.MainPageService;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@ContextConfiguration(classes = MainPageController.class)
@WebMvcTest(MainPageController.class)

public class MainPageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MainPageService mainPageService;

    @Test
    void shouldReturnArticleInfoOnGETRequest() throws  Exception {
        String pubSubject = "Пример заголовка";
        String pubDescription = "Описание статьи.";
        String pubDate = LocalDate.now().toString();

        given(mainPageService.fetchPublicationsSubject()).willReturn(pubSubject);
        given(mainPageService.fetchPublicationsDescription()).willReturn(pubDescription);
        given(mainPageService.fetchPublicationDate()).willReturn(pubDate);

        this.mockMvc.perform(get("/api/editingPage"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"publicationsSubject\":\"" + pubSubject + "\"")))
                .andExpect(content().string(containsString("\"publicationsDescription\":\"" + pubDescription + "\"")))
                .andExpect(content().string(containsString("\"publicationDate\":\"" + pubDate + "\"")));
    }

    @Test
    void shouldHandleEmptyResponseGracefully() throws Exception {
        // Случай, когда сервисы возвращают пустые строки
        given(mainPageService.fetchPublicationsSubject()).willReturn("Publications are temporarily unavailable");
        given(mainPageService.fetchPublicationsDescription()).willReturn("Publications are temporarily unavailable");
        given(mainPageService.fetchPublicationDate()).willReturn("No information available");

        this.mockMvc.perform(get("/api/editingPage"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"publicationsSubject\":\"Publications are temporarily unavailable\"")))
                .andExpect(content().string(containsString("\"publicationsDescription\":\"Publications are temporarily unavailable\"")))
                .andExpect(content().string(containsString("\"publicationDate\":\"No information available\"")));
    }

    @Test
    void shouldReturnNotFoundForInvalidRoute() throws Exception {
        this.mockMvc.perform(get("/api/wrongPath"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestForPostMethod() throws Exception {
        this.mockMvc.perform(post("/api/editingPage"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());}
}
