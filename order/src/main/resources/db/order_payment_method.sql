DROP TABLE IF EXISTS `payment_method`;

CREATE TABLE `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `is_default` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `payment_method` (`id`, `user_id`, `name`, `provider`, `account_number`, `expiry_date`, `is_default`)
VALUES (1,NULL,'cod',NULL,NULL,NULL,_binary ''),
       (2,NULL,'vnpay','','',NULL,_binary '\0');