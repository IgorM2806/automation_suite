
import article.controllers.SearchPublicationController;
import article.model.Article;
import article.service.PublicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StopWatch;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;


import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SearchPublicationController.class})
@WebMvcTest(controllers = SearchPublicationController.class)
public class SearchPublicationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PublicationService publicationService;

    @Test
    void testEditPost_Success() throws Exception {
        String title = "Тестовая статья";
        Article article = new Article();
        article.setTitle(title);

        when(publicationService.findSimilarArticleByTitle(anyString())).thenReturn(article);

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("title", title);

        // Отправляем POST-запрос и проверяем результат
        mockMvc.perform(
                        post("/api/editing")      // делаем POST-запрос
                                .content(objectMapper.writeValueAsBytes(requestData))     // отправляем тело запроса
                                .contentType(MediaType.APPLICATION_JSON)                   // задаём Content-Type
                )
                .andExpect(status().isOk())              // проверяем, что получили ОК-статус
                .andExpect(jsonPath("$.title", is(title))) ; // проверяем значение title в результате
    }

    @Test
    void testEditPost_NotFound() throws Exception {
        when(publicationService.findSimilarArticleByTitle(anyString())).thenReturn(null);

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("title", "Отсутствующая статья");

        mockMvc.perform(post("/api/editing")
        .content(objectMapper.writeValueAsBytes(requestData))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEmptyTitleValue() throws Exception {
        Map<String, Object> requestBody = Map.of("title", "");
        String requestBodyString = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyString))
                            .andExpect(status().isNotFound());
    }

    @Test
    void testMissingHeaderRequest() throws Exception {
        Map<String, Object> emptyRequestBody = Collections.emptyMap();
        String requestBodyString = objectMapper.writeValueAsString(emptyRequestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBodyString))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInvalidDataFormat() throws Exception {
        Map<String, Object> requestData = Map.of("title", 123);
        String requestBodyString = objectMapper.writeValueAsString(requestData);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestBodyString)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void largeTitle() throws Exception {
        String largeTitle = randomAlphabetic(5_000);
        Map<String, Object> requestData = Map.of("title", largeTitle);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSpecialCharactersInTitle() throws Exception {
        String specialCharsTitle = "<script>alert('XSS')</script>";
        Map<String, Object> requestData = Map.of("title", specialCharsTitle);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestData)))
                .andExpect(status().isBadRequest());
    }

    // ТЕСТ НА РАЗРЕШЁННЫЕ ИЛИ ЗАПРЕЩЁННЫЕ СИМУЛЬТАЦИОННЫЕ ЗАПРОСЫ
    @Test
    void testConcurrentRequests() throws Exception {
        int numThreads = 10;
        CountDownLatch latch = new CountDownLatch(numThreads);

        for(int i=0; i<numThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    mockMvc.perform(post("/api/editing")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(Map.of("title", "concurrent"))));
                } catch(Exception e) {}
                finally {
                    latch.countDown();
                }
            });
            thread.start();
        }
        latch.await();
    }
}