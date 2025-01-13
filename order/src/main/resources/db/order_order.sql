DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` int NOT NULL,
                         `order_date` datetime(6) NOT NULL,
    `payment_method_id` int NOT NULL,
    `address_id` int NOT NULL,
    `total_amount` double NOT NULL,
    `shipping_method_id` int NOT NULL,
    `order_status` varchar(50) NOT NULL,
    `payment_status` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `payment_method_id` (`payment_method_id`),
    KEY `shipping_method_id` (`shipping_method_id`),
    CONSTRAINT `order_ibfk_1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
    CONSTRAINT `order_ibfk_2` FOREIGN KEY (`shipping_method_id`) REFERENCES `shipping_method` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `order` (`id`, `user_id`, `order_date`, `payment_method_id`, `address_id`, `total_amount`, `shipping_method_id`, `order_status`, `payment_status`)
VALUES (32,34,'2024-12-30 16:25:31.663039',2,18,26049000,2,'Hoàn thành','Hoàn thành'),
       (33,34,'2024-12-30 20:58:05.911519',1,18,174097000,3,'Đang giao','Chưa thanh toán'),
       (35,32,'2024-12-30 21:23:14.573652',1,16,11819000,1,'Đang chuẩn bị','Chưa thanh toán'),
       (36,33,'2024-12-30 21:34:39.103588',1,17,13219000,1,'Chờ xác nhận','Chưa thanh toán'),
       (37,38,'2025-01-04 01:53:44.482854',2,20,32099000,3,'Đang chuẩn bị','Hoàn thành'),
       (38,33,'2025-01-04 07:45:20.330759',1,17,35019000,1,'Chờ xác nhận','Chưa thanh toán'),
       (39,34,'2025-01-04 07:55:27.409584',1,18,32019000,1,'Chờ xác nhận','Chưa thanh toán'),
       (40,40,'2025-01-04 08:27:03.134649',2,21,32099000,3,'Hoàn thành','Hoàn thành');