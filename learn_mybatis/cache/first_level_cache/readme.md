# MyBatis First Level Cache

- [MyBatis First Level Cache](#mybatis-first-level-cache)
  - [Reference](#reference)
    - [MyBatis first level cache example](#mybatis-first-level-cache-example)

## Reference

### [MyBatis first level cache example](http://www.henryxi.com/mybatis-first-level-cache-example)

The first level cache is enabled by default in MyBatis. It will cache the result after querying data from database. The second time you query MyBatis won't hit db. The first level cache is for session. If the session is close the cache will be cleared. I assume that you have created database and insert some data in your table.

table create sql

    CREATE TABLE users
    (
      id INT PRIMARY KEY AUTO_INCREMENT,
      user_name VARCHAR(50),
      password VARCHAR(50)
    );
