CREATE TABLE IF NOT EXISTS `t_todo`
(
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `title` varchar(200) NULL DEFAULT NULL COMMENT 'title',
  `completed` boolean NULL DEFAULT NULL COMMENT 'completed',
  `create_time` datetime NOT NULL COMMENT 'create time',
  `update_time` datetime NULL DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `user_name` varchar(64) DEFAULT NULL COMMENT 'user name',
  `user_password` varchar(64) DEFAULT NULL COMMENT 'user password',
  `user_type` tinyint(4) DEFAULT NULL COMMENT 'user type, 0:administrator，1:ordinary user',
  `email` varchar(64) DEFAULT NULL COMMENT 'email',
  `phone` varchar(11) DEFAULT NULL COMMENT 'phone',
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_session` (
  `id` varchar(64) NOT NULL COMMENT 'key',
  `user_id` int(11) DEFAULT NULL COMMENT 'user id',
  `ip` varchar(45) DEFAULT NULL COMMENT 'ip',
  `last_login_time` datetime DEFAULT NULL COMMENT 'last login time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_access_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `user_id` int(11) DEFAULT NULL COMMENT 'user id',
  `token` varchar(64) DEFAULT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT 'end time of token ',
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
