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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SearchPublicationController.class)
@WebMvcTest(controllers = SearchPublicationController.class)

public class EditingControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    PublicationService publicationService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testEditPost_Successful() throws Exception {
        String existingTitle = "My Existing Title";
        Article expectedArticle = new Article("Author", existingTitle, LocalDate.now(),
                "Test content", null);
        given(publicationService.findSimilarArticleByTitle(anyString())).willReturn(expectedArticle);

        Map<String, Object> requestBody = Map.of("title", existingTitle);
        String requestBodyString = objectMapper.writeValueAsString(requestBody);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyString))
                .andExpect(status().isOk())
                .andReturn();
        Article actualArticle = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Article.class);
        assertThat(actualArticle.getTitle()).isEqualTo(existingTitle);
    }

    @Test
    void testEditPost_Failed() throws Exception {
        String nonExistentTitle = "non-existent title";

        given(publicationService.findSimilarArticleByTitle(nonExistentTitle)).willReturn(null);

        Map<String, Object> requestBody = Map.of("title", nonExistentTitle);
        String requestBodyString = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyString))
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
    void testMissingHeaderRequest() throws Exception{

        Map<String, Object> emptyRequestBody = Collections.emptyMap();
        String requestBodyString = objectMapper.writeValueAsString(emptyRequestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/editing")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBodyString))
                .andExpect(status().isNotFound());
    }
}
