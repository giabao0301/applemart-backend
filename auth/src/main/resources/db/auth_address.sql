DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `address_type` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `is_delivery_address` bit(1) DEFAULT NULL,
  `recipient` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `ward` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id`),
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `address` (`id`, `address`, `address_type`, `city`, `district`, `is_delivery_address`, `recipient`, `phone`, `ward`, `user_id`)
VALUES (3,'Ktx Khu A Đhqg, Đường Tạ Quang Bửu, Khu Phố 6','Nhà riêng','TP. Hồ Chí Minh','Thành Phố Thủ Đức',_binary '','Trịnh Gia Bảo','0898889998','Phường Linh Trung',7),
       (4,'Ấp Giồng Giữa A','Nhà riêng','Bạc Liêu','Bạc Liêu',_binary '','Nguyễn A Nhú','0898889998','Xã Vĩnh Trạch Đông',2),
       (8,'Ktx Khu A Đhqg, Đường Tạ Quang Bửu, Khu Phố 6','Nhà riêng','Hồ Chí Minh','Thủ Đức',_binary '\0','Trịnh Gia Bảo','0987665432','Phường Linh Trung',2),
       (13,'KTX khu a đhqg','Nhà riêng','Hồ Chí Minh','Thủ Đức',_binary '','Trịnh Gia Bảo','0987665443','Phường Linh Trung',26),
       (14,'ktx khu a đhqg','Nhà riêng','Hồ Chí Minh','Thủ Đức',_binary '','Trịnh Gia Bảo','09876554332','Phường Linh Trung',27),
       (15,'ktx khu a đhqg','Nhà riêng','Hồ Chí Minh','Thủ Đức',_binary '','Trịnh Gia Bảo','0987665443','Phường Linh Trung',28);