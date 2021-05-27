DELETE FROM t_todo;

INSERT INTO t_todo (id, title, completed, create_time) VALUES
(1, 'task-1', TRUE, CURRENT_TIMESTAMP()),
(2, 'task-2', FALSE, CURRENT_TIMESTAMP()),
(3, 'task-3', FALSE, CURRENT_TIMESTAMP());
