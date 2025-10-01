import article.controllers.MainPageController;
import article.service.MainPageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ContextConfiguration(classes = MainPageController.class)
@WebMvcTest(controllers = MainPageController.class)
public class EditingPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    MainPageService mainPageService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGettingInformationArticle() throws Exception {
        when(mainPageService.fetchPublicationsSubject()).thenReturn("Тестовая публикация");
        when(mainPageService.fetchPublicationsDescription()).thenReturn("Описание публикации");
        when(mainPageService.fetchPublicationDate()).thenReturn("2025-09-01");

        Map<String,Object> expectedResponse = new HashMap<>();
        expectedResponse.put("publicationsSubject", "Тестовая публикация");
        expectedResponse.put("publicationsDescription", "Описание публикации");
        expectedResponse.put("publicationDate", "2025-09-01");

        this.mockMvc.perform(get("/api/editingPage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicationsSubject", equalTo("Тестовая публикация")))
                .andExpect(jsonPath("$.publicationsDescription", equalTo("Описание публикации")))
                .andExpect(jsonPath("$.publicationDate", equalTo("2025-09-01")));
    }

    @Test
    void testUtfCharacters() throws Exception {
        when(mainPageService.fetchPublicationsSubject()).thenReturn("Испытание 🌍 символами");
        when(mainPageService.fetchPublicationsDescription()).thenReturn("Символьное испытание 📚");
        when(mainPageService.fetchPublicationDate()).thenReturn("2025-09-01");

        Map<String,Object> expectedResponse = new HashMap<>();
        expectedResponse.put("publicationsSubject", "Испытание 🌍 символами");
        expectedResponse.put("publicationsDescription", "Символьное испытание 📚");
        expectedResponse.put("publicationDate", "2025-09-01");
        this.mockMvc.perform(get("/api/editingPage"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(expectedResponse)));
    }

    private String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
