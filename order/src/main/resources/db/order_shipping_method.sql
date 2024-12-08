DROP TABLE IF EXISTS `shipping_method`;

CREATE TABLE `shipping_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `shipping_method` (`id`, `name`, `price`) VALUES (1,'Tiết kiệm',20000),(2,'Nhanh',50000),(3,'Hỏa tốc',100000);
