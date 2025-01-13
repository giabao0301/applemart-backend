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
    ) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `order_line` (`id`, `order_id`, `product_item_id`, `quantity`, `price`, `note`)
VALUES (33,32,100,1,25999000,NULL),
       (34,33,148,1,40999000,NULL),
       (35,33,200,2,132998000,NULL),
       (37,35,263,1,11799000,NULL),
       (38,36,276,1,13199000,NULL),
       (39,37,90,1,31999000,NULL),
       (40,38,120,1,34999000,NULL),
       (41,39,94,1,31999000,NULL),
       (42,40,90,1,31999000,NULL);