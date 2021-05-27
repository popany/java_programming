DROP TABLE IF EXISTS t_todo;

CREATE TABLE t_todo
(
    id BIGINT(20) NOT NULL COMMENT 'Primary key',
    title VARCHAR(200) NULL DEFAULT NULL COMMENT 'title',
    completed BOOLEAN NULL DEFAULT NULL COMMENT 'completed',
    create_time TIMESTAMP NOT NULL COMMENT 'create time',
    update_time TIMESTAMP NULL DEFAULT NULL COMMENT 'update time',
    PRIMARY KEY (id)
);
