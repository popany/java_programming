INSERT INTO t_user (id, user_name, user_password, user_type, create_time) VALUES
(1, 'admin', '123', 0, CURRENT_TIMESTAMP()) ON DUPLICATE KEY UPDATE id=id;
