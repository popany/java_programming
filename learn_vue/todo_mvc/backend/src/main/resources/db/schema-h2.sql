DROP TABLE IF EXISTS t_todo;

CREATE TABLE t_todo
(
    id BIGINT(20) NOT NULL COMMENT 'Primary key',
    title VARCHAR(200) NULL DEFAULT NULL COMMENT 'title',
    completed BOOLEAN NULL DEFAULT NULL COMMENT 'completed',
    PRIMARY KEY (id)
);
