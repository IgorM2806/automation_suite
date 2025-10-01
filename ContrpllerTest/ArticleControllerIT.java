import article.controllers.ArticleController;
import article.dto.ArticleDto;
import article.model.Article;
import article.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ArticleController.class)
@WebMvcTest (controllers = ArticleController.class)
public class ArticleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  ObjectMapper objectMapper;

    @BeforeEach
    void setupObjectMapper()  {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @MockitoBean
    private ArticleRepository articleRepository;

    @Test
    void testCreateArticleSuccessful() throws Exception {

        ArticleDto articleDto = new ArticleDto("Иван Иванов", "Тестовая статья",
                LocalDate.now(), "Это содержание статьи.");

        Article savedArticle = new Article(articleDto.getAuthor(), articleDto.getTitle(),
                articleDto.getDatepublished(), articleDto.getContent());
        savedArticle.setId(1L);

        given(articleRepository.save(any(Article.class))).willReturn(savedArticle);

        String json = objectMapper.writeValueAsString(articleDto);

        mockMvc.perform(post("/api/publication")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // ожидаем статус 201
                .andExpect(jsonPath("$.id", is(savedArticle.getId().intValue()))) // проверка значения id
                .andExpect(jsonPath("$.author", is(savedArticle.getAuthor()))) // проверка значения author
                .andExpect(jsonPath("$.title", is(savedArticle.getTitle()))) // проверка значения title
                .andExpect(jsonPath("$.publicationDate", is(savedArticle.getPublicationDate().toString()))) // проверка даты
                .andExpect(jsonPath("$.content", is(savedArticle.getContent()))) // проверка содержания
                .andExpect(jsonPath("$.link", is(savedArticle.getLink()))); // проверка ссылки
    }

    @Test
    void testUpdateArticleSuccessful() throws Exception {
        Article exisningArticle =new Article("Автор", "Название статьи", LocalDate.now(),
                null, "https://ya.ru/?npr=1");
        exisningArticle.setId(1L);
        ArticleDto updateDTO = new ArticleDto("Новый автор", "Новое название статьи",
                LocalDate.now().plusDays(1),
                null, "https://ya.ru/?npr=1");

        when(articleRepository.findById(exisningArticle.getId().intValue())).thenReturn(Optional.of(exisningArticle));
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> i.getArguments()[0]);
        String json = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(patch("/api/publication/" + exisningArticle.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", is(exisningArticle.getAuthor())))
                .andExpect(jsonPath("$.title", is(exisningArticle.getTitle())));
    }

    // Тест на обновление несуществующей статьи
    @Test
    void testUpdateNonexistentArticle()  throws Exception {
        int nonexistentId = 999;
        ArticleDto updateDTO = new ArticleDto("Автор", "Название статьи", LocalDate.now(),
                "Контент", null);
        when(articleRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        String requestJson = objectMapper.writeValueAsString(updateDTO);
        mockMvc.perform(patch("/api/publication/" + nonexistentId)
        .content(requestJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void testCreateInvalidAuthor() throws Exception {
        ArticleDto inValidDTO = new ArticleDto(null, "Заголовок", LocalDate.now(),
                "Содержание", null);
        String requestJson = objectMapper.writeValueAsString(inValidDTO);

        mockMvc.perform(post("/api/publication")
        .content(requestJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testCreateInvalidTitle() throws Exception {
        ArticleDto invalidTitle = new ArticleDto("Автор", null, LocalDate.now(),
                "Содержание статьи",  null);
        String requestJson = objectMapper.writeValueAsString(invalidTitle);
        mockMvc.perform(post("/api/publication")
                .content(requestJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateInvalidDate() throws Exception {
        ArticleDto invalidDTO = new ArticleDto("Автор", "Название статьи", null,
                "Содержание статьи", null);
        String requestJson = objectMapper.writeValueAsString(invalidDTO);
        mockMvc.perform(post("/api/publication")
        .content(requestJson)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
