import article.model.Article;
import article.repository.ArticleRepository;
import article.service.MainPageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MainPageServiceTest {

    @InjectMocks
    private MainPageService mainPageService;

    @Mock
    private ArticleRepository repository;

    @Test
    void testFetchPublicationDateWhenLastArticleExists() throws Exception{
        Article mockArticle = new Article();
        mockArticle.setPublicationDate(LocalDate.now());

        given(repository.findFirstByOrderByIdDesc()).willReturn(Optional.of(mockArticle));

        assertThat(mainPageService.fetchPublicationDate()).isEqualTo(mockArticle.getPublicationDate().toString());
    }

    @Test
    void testFetchPublicationDateWhenNoArticlesAvailable()  throws Exception{
        given(repository.findFirstByOrderByIdDesc()).willReturn(Optional.empty());

        assertThat(mainPageService.fetchPublicationDate()).isEqualTo("No information available");
    }

    @Test
    void testExtractFirstTwoSentencesFromText() throws Exception{
        String inputText = "Это первое предложение. Это второе предложение.";
        String expectedOutput = "Это первое предложение.";

        assertThat(mainPageService.extractFirstTwoSentences(inputText)).isEqualTo(expectedOutput);
    }

    @Test
    void testExtractFirstTwoSentencesFromOneSentenceText()  throws Exception{
        // Когда имеется всего одно предложение
        String inputText = "Только одно предложение.";
        String expectedOutput = "Только одно предложение.";

        assertThat(mainPageService.extractFirstTwoSentences(inputText)).isEqualTo(expectedOutput);
    }

    @Test
    void testFetchPublicationsSubjectWithValidData() throws Exception{
        Article mockArticle = new Article();
        mockArticle.setTitle("Тестовая статья");
        given(repository.findFirstByOrderByIdDesc()).willReturn(Optional.of(mockArticle));

        assertThat(mainPageService.fetchPublicationsSubject()).isEqualTo("Тестовая статья");
    }

    @Test
    void testFetchPublicationsSubjectWithoutAnyData() throws Exception{
        given(repository.findFirstByOrderByIdDesc()).willReturn(Optional.empty());

        assertThat(mainPageService.fetchPublicationsSubject())
                .isEqualTo("Publications are temporarily unavailable");
    }

    @Test
    void testFetchPublicationsDescriptionWithValidData() {
        Article mockArticle = new Article();
        mockArticle.setContent("Первый абзац первой статьи.");
        given(repository.findFirstByOrderByIdDesc()).willReturn(Optional.of(mockArticle));

        assertThat(mainPageService.fetchPublicationsDescription()).isEqualTo("Первый абзац первой статьи.");
    }

}
