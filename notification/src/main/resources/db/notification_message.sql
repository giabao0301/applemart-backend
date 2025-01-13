DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `chat_room_id` bigint DEFAULT NULL,
    `content` varchar(255) DEFAULT NULL,
    `receiver_id` bigint DEFAULT NULL,
    `sender_id` bigint DEFAULT NULL,
    `timestamp` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 21 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

