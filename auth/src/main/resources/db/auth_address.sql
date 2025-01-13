
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
    CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `address` (`id`, `address`, `address_type`, `city`, `district`, `is_delivery_address`, `recipient`, `phone`, `ward`, `user_id`)
VALUES (16,'Ktx khu A đhqg, đường Tạ Quang Bửu','','Thành phố Hồ Chí Minh','Thành phố Thủ Đức',_binary '','Trịnh Gia Bảo','0987665098','Phường Linh Trung',32),
       (17,'Ấp Giồng Giữa B','','Tỉnh Bạc Liêu','Thành phố Bạc Liêu',_binary '','Nguyễn A Nhú','0876554332','Xã Vĩnh Trạch Đông',33),
       (18,'Đường Trần Phú','Nhà riêng','Tỉnh An Giang','Huyện An Phú',_binary '','Nguyễn Văn B','0987665443','Thị trấn An Phú',34),
       (21,'Đường abc','','Tỉnh An Giang','Huyện An Phú',_binary '','Trịnh Gia Bảo','0988776554','Xã Nhơn Hội',40);
