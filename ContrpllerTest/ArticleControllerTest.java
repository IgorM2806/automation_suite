import article.controllers.ArticleController;
import article.dto.ArticleDto;
import article.model.Article;
import article.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

@ContextConfiguration(
        classes = {ArticleController.class}
)
@WebMvcTest(
        controllers = {ArticleController.class}
)
public class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ArticleRepository articleRepository;

    @BeforeEach
    void setupObjectMapper() {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreateArticleSuccessful() throws Exception {
        ArticleDto articleDto = new ArticleDto("Иван Иванов", "Тестовая статья", LocalDate.now(), "Это содержание статьи.");
        Article savedArticle = new Article(articleDto.getAuthor(), articleDto.getTitle(), articleDto.getDatepublished(), articleDto.getContent());
        savedArticle.setId(1L);
        BDDMockito.given((Article)this.articleRepository.save((Article) ArgumentMatchers.any(Article.class))).willReturn(savedArticle);
        String json = this.objectMapper.writeValueAsString(articleDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/publication", new Object[0]).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(savedArticle.getId().intValue()))).andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.is(savedArticle.getAuthor()))).andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(savedArticle.getTitle()))).andExpect(MockMvcResultMatchers.jsonPath("$.publicationDate", Matchers.is(savedArticle.getPublicationDate().toString()))).andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(savedArticle.getContent()))).andExpect(MockMvcResultMatchers.jsonPath("$.link", Matchers.is(savedArticle.getLink())));
    }

    @Test
    void testUpdateArticleSuccessful() throws Exception {
        Article exisningArticle = new Article("Автор", "Название статьи", LocalDate.now(), (String)null, "https://ya.ru/?npr=1");
        exisningArticle.setId(1L);
        ArticleDto updateDTO = new ArticleDto("Новый автор", "Новое название статьи", LocalDate.now().plusDays(1L), (String)null, "https://ya.ru/?npr=1");
        Mockito.when(this.articleRepository.findById(exisningArticle.getId().intValue())).thenReturn(Optional.of(exisningArticle));
        Mockito.when((Article)this.articleRepository.save((Article)ArgumentMatchers.any(Article.class))).thenAnswer((i) -> i.getArguments()[0]);
        String json = this.objectMapper.writeValueAsString(updateDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/publication/" + exisningArticle.getId(), new Object[0]).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.is(exisningArticle.getAuthor()))).andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(exisningArticle.getTitle())));
    }

    @Test
    void testUpdateNonexistentArticle() throws Exception {
        int nonexistentId = 999;
        ArticleDto updateDTO = new ArticleDto("Автор", "Название статьи", LocalDate.now(), "Контент", (String)null);
        Mockito.when(this.articleRepository.findById(nonexistentId)).thenReturn(Optional.empty());
        String requestJson = this.objectMapper.writeValueAsString(updateDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/publication/" + nonexistentId, new Object[0]).content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateInvalidAuthor() throws Exception {
        ArticleDto inValidDTO = new ArticleDto((String)null, "Заголовок", LocalDate.now(), "Содержание", (String)null);
        String requestJson = this.objectMapper.writeValueAsString(inValidDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/publication", new Object[0]).content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCreateInvalidTitle() throws Exception {
        ArticleDto invalidTitle = new ArticleDto("Автор", (String)null, LocalDate.now(), "Содержание статьи", (String)null);
        String requestJson = this.objectMapper.writeValueAsString(invalidTitle);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/publication", new Object[0]).content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCreateInvalidDate() throws Exception {
        ArticleDto invalidDTO = new ArticleDto("Автор", "Название статьи", (LocalDate)null, "Содержание статьи", (String)null);
        String requestJson = this.objectMapper.writeValueAsString(invalidDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/publication", new Object[0]).content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
