DROP TABLE IF EXISTS `order_line`;

CREATE TABLE `order_line` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_item_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_line_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `order_line` (`id`, `order_id`, `product_item_id`, `quantity`, `price`, `note`)
VALUES (1,1,2,1,149994000,NULL),
       (2,2,1,1,24999000,NULL),
       (4,4,1,1,24999000,NULL),
       (20,19,52,2,204998000,NULL),
       (21,20,32,1,42999000,NULL),
       (22,21,5,1,29999000,NULL),
       (23,22,17,1,37999000,NULL),
       (24,23,6,2,59998000,NULL),
       (25,24,17,2,75998000,NULL),
       (26,25,8,1,29999000,NULL);
