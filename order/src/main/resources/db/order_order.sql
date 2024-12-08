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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `order` (`id`, `user_id`, `order_date`, `payment_method_id`, `address_id`, `total_amount`, `shipping_method_id`, `order_status`, `payment_status`)
VALUES (1,2,'2024-11-14 10:24:48.954524',2,1,25049000,2,'Hoàn thành','Hoàn thành'),
       (2,2,'2024-10-14 16:38:14.808771',2,1,25049000,2,'Đã hủy','Chưa thanh toán'),
       (4,7,'2024-10-14 21:24:33.484786',2,3,25049000,2,'Chờ xác nhận','Chưa thanh toán'),
       (19,2,'2024-11-29 22:19:03.880819',1,4,205018000,1,'Chờ xác nhận','Đang chờ'),
       (20,2,'2024-11-29 22:52:13.910148',2,4,43019000,1,'Chờ xác nhận','Hoàn thành'),
       (21,26,'2024-11-30 08:47:23.176963',2,13,30019000,1,'Chờ xác nhận','Đang chờ'),
       (22,26,'2024-11-30 08:49:16.517469',2,13,38019000,1,'Chờ xác nhận','Hoàn thành'),
       (23,27,'2024-11-30 09:00:09.942297',2,14,60018000,1,'Chờ xác nhận','Hoàn thành'),
       (24,2,'2024-11-30 09:08:27.834727',2,4,76048000,2,'Chờ xác nhận','Hoàn thành'),
       (25,28,'2024-11-30 09:15:06.133026',2,15,30049000,2,'Chờ xác nhận','Hoàn thành');