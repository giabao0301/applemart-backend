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
    CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
