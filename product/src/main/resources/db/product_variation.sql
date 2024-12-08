DROP TABLE IF EXISTS `variation`;

CREATE TABLE `variation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `variation_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `variation` (`id`, `category_id`, `name`)
VALUES (1,7,'Màu'),
       (2,7,'RAM'),
       (3,7,'Ổ cứng'),
       (4,8,'Màu'),
       (5,8,'RAM'),
       (6,8,'Ổ cứng'),
       (7,9,'Màu'),
       (8,9,'RAM'),
       (9,9,'Ổ cứng'),
       (15,10,'RAM'),
       (16,10,'Ổ cứng'),
       (18,11,'Màn hình'),
       (19,11,'Chân đế'),
       (20,12,'Màn hình'),
       (21,2,'Màu'),
       (22,2,'Dung lượng lưu trữ');