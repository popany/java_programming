// https://www.baeldung.com/spring-mybatis

package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;
 public class AppUseMybatis 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            ArticleMapper articleMapper = (ArticleMapper)context.getBean("articleMapper");

            Article article = articleMapper.getArticle(1L);
            System.out.println("============================================");
            System.out.println("id: " + article.getId());
            System.out.println("title: " + article.getTitle());
            System.out.println("author: " + article.getAuthor());
            System.out.println("--------------------------------------------");
        }
    }
}
