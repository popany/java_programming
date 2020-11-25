package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/beans.xml")
public class AppTestUseMybatis 
{
    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void whenRecordsInDatabase_shouldReturnArticleWithGivenId() {
        Article article = articleMapper.getArticle(1L);
 
        assertNotNull(article);
        assertThat((Object)article).isNotNull();
        assertThat(article.getId()).isEqualTo(1L);
        assertThat(article.getAuthor()).isEqualTo("Baeldung");
        assertThat(article.getTitle()).isEqualTo("Working with MyBatis in Spring");
    }
}
