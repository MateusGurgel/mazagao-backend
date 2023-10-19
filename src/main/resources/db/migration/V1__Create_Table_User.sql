CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `game_rank` varchar(255) NOT NULL DEFAULT 'Sem rank',
  `score` int NOT NULL,
  `deaths` int NOT NULL,
  `kills` int NOT NULL,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  PRIMARY KEY (`id`)
);
