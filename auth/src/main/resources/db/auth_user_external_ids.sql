DROP TABLE IF EXISTS `user_external_ids`;

CREATE TABLE `user_external_ids` (
    `user_id` int NOT NULL,
    `external_id` varchar(255) DEFAULT NULL,
    `provider` varchar(255) NOT NULL,
    PRIMARY KEY (`user_id`, `provider`),
    CONSTRAINT `FKjcnclpmfap9ncsldogmp3ctam` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO
    `user_external_ids` (`user_id`, `external_id`, `provider`)
VALUES
    (32, '112551455381505985046', 'google'),
    (33, '111587011920518588528', 'google'),
    (34, '113880953012866259556', 'google'),
    (40, '108525473372007833061', 'google');