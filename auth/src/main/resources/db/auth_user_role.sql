DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (2,1),
       (7,1),
       (8,1),
       (11,1),
       (12,1),
       (13,1),
       (14,1),
       (15,1),
       (16,1),
       (17,1),
       (19,1),
       (20,1),
       (21,1),
       (22,1),
       (23,1),
       (24,1),
       (25,1),
       (26,1),
       (27,1),
       (28,1),
       (30,1),
       (1,2);