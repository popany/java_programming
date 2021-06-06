package org.example.first_level_cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.first_level_cache.entity.User;
import org.example.first_level_cache.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client
{
    static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            Map<String, String> envs = System.getenv();
            Properties properties = new Properties();
            properties.putAll(envs);
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
        } catch (IOException e) {
            logger.error(resource, e);
        }
    }

    public static void main( String[] args )
    {
        if (sqlSessionFactory == null) {
            return;
        }

        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User firstSelectWithCache = mapper.selectByIdWithCache(1);
            logger.info("first query with cache: {}", firstSelectWithCache.getUserName());

            User secondSelectWithCache = mapper.selectByIdWithCache(1);
            logger.info("second query with cache: {}", secondSelectWithCache.getUserName());

            User firstSelectWithoutCache = mapper.selectByIdWithoutCache(1);
            logger.info("first query without cache: {}", firstSelectWithoutCache.getUserName());

            User secondSelectWithoutCache = mapper.selectByIdWithoutCache(1);
            logger.info("second query without cache: {}", secondSelectWithoutCache.getUserName());

            sqlSession.commit();
        } catch (PersistenceException e) {
            logger.error(null, e);
        } finally {
            sqlSession.close();
        }
    }
}
