DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
    `id` int NOT NULL AUTO_INCREMENT,
    `message` varchar(255) DEFAULT NULL,
    `sender` varchar(255) DEFAULT NULL,
    `sent_at` datetime(6) DEFAULT NULL,
    `to_user_email` varchar(255) DEFAULT NULL,
    `to_user_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;