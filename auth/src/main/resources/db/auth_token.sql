DROP TABLE IF EXISTS `token`;

CREATE TABLE `token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id`),
  CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `token` (`id`, `created_at`, `expires_at`, `token`, `type`, `user_id`)
VALUES (1,'2024-12-01 00:08:14.648014','2024-12-02 00:08:14.648014','041339','confirmation',30),
       (3,'2024-12-01 12:12:41.178743','2024-12-02 12:12:41.178743','398675','confirmation',2),
       (4,'2024-12-01 12:19:35.404118','2024-12-01 12:49:35.404118','154007','password-reset',2),
       (5,'2024-12-01 12:29:48.066082','2024-12-01 12:59:48.066082','355153','password-reset',2),
       (6,'2024-12-01 13:08:40.811015','2024-12-01 13:38:40.811015','029077','password-reset',2);
