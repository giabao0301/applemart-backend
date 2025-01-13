DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `user1id` bigint DEFAULT NULL,
    `user2id` bigint DEFAULT NULL,
    `timestamp` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
