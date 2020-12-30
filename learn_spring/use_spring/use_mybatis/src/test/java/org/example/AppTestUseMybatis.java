package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/beans.xml")
public class AppTestUseMybatis 
{
    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void whenRecordsInDatabase_shouldReturnArticleWithGivenId() {
        Article article = articleMapper.getArticle(1L);
 
        assertNotNull(article);
        assertEquals((Long)1L, article.getId());
        assertEquals("Baeldung", article.getAuthor());
        assertEquals("Working with MyBatis in Spring", article.getTitle());
    }
}
