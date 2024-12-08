DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `category_id` int NOT NULL,
  `lowest_price` double NOT NULL,
  `thumbnail_url` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `product` (`id`, `name`, `category_id`, `lowest_price`, `thumbnail_url`, `slug`, `description`)
VALUES (1,'MacBook Air 13 inch M2',7,24999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303485/mba13-midnight-select-202402_bb0ypi.jpg','macbook-air-13-inch-m2','MacBook Air dễ dàng xử lý cả công việc lẫn giải trí và chip M2 mang đến năng lực tuyệt vời cho chiếc máy tính xách tay siêu gọn nhẹ này. Với thời lượng pin lên đến 18 giờ, bạn có thể mang theo chiếc MacBook Air siêu gọn nhẹ này đi bất cứ nơi đâu và nhanh chóng hoàn thành mọi việc bạn muốn.'),
       (2,'MacBook Air 13 inch M3',7,27999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303485/mba13-midnight-select-202402_bb0ypi.jpg','macbook-air-13-inch-m3','MacBook Air dễ dàng xử lý cả công việc lẫn giải trí và chip M3 mang đến năng lực tuyệt vời cho chiếc máy tính xách tay siêu gọn nhẹ này. Với thời lượng pin lên đến 18 giờ, bạn có thể mang theo chiếc MacBook Air siêu gọn nhẹ này đi bất cứ nơi đâu và nhanh chóng hoàn thành mọi việc bạn muốn.'),
       (3,'MacBook Air 15 inch M3',7,32999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303485/mba13-midnight-select-202402_bb0ypi.jpg','macbook-air-15-inch-m3','MacBook Air dễ dàng xử lý cả công việc lẫn giải trí và chip M3 mang đến năng lực tuyệt vời cho chiếc máy tính xách tay siêu gọn nhẹ này. Với thời lượng pin lên đến 18 giờ, bạn có thể mang theo chiếc MacBook Air siêu gọn nhẹ này đi bất cứ nơi đâu và nhanh chóng hoàn thành mọi việc bạn muốn.'),
       (4,'MacBook Pro 16 inch M3 Max',8,89999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303760/mbp14-m3-max-pro-spaceblack-select-202310_pbhujj.jpg','macbook-pro-16-inch-m3-max','MacBook Pro chạy cực nhanh với chip M3, M3 Pro và M3 Max. Được thiết kế trên công nghệ 3 nanômét và sở hữu kiến trúc GPU hoàn toàn mới, đây là dòng chip tiên tiến nhất từng được thiết kế cho máy tính cá nhân. Và mỗi mẫu chip trong đó đều mang đến nhiều tính năng và hiệu năng chuyên nghiệp hơn.'),
       (5,'MacBook Pro 14 inch M3 Pro',8,49999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303760/mbp14-m3-max-pro-spaceblack-select-202310_pbhujj.jpg','macbook-pro-14-inch-m3-pro','MacBook Pro chạy cực nhanh với chip M3, M3 Pro và M3 Max. Được thiết kế trên công nghệ 3 nanômét và sở hữu kiến trúc GPU hoàn toàn mới, đây là dòng chip tiên tiến nhất từng được thiết kế cho máy tính cá nhân. Và mỗi mẫu chip trong đó đều mang đến nhiều tính năng và hiệu năng chuyên nghiệp hơn.'),
       (6,'MacBook Pro 14 inch M3 Pro',8,49999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303760/mbp14-m3-max-pro-spaceblack-select-202310_pbhujj.jpg','macbook-pro-14-inch-m3-pro','MacBook Pro chạy cực nhanh với chip M3, M3 Pro và M3 Max. Được thiết kế trên công nghệ 3 nanômét và sở hữu kiến trúc GPU hoàn toàn mới, đây là dòng chip tiên tiến nhất từng được thiết kế cho máy tính cá nhân. Và mỗi mẫu chip trong đó đều mang đến nhiều tính năng và hiệu năng chuyên nghiệp hơn.'),
       (7,'MacBook Pro 16 inch M3 Pro',8,64999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303760/mbp14-m3-max-pro-spaceblack-select-202310_pbhujj.jpg','macbook-pro-16-inch-m3-pro','MacBook Pro chạy cực nhanh với chip M3, M3 Pro và M3 Max. Được thiết kế trên công nghệ 3 nanômét và sở hữu kiến trúc GPU hoàn toàn mới, đây là dòng chip tiên tiến nhất từng được thiết kế cho máy tính cá nhân. Và mỗi mẫu chip trong đó đều mang đến nhiều tính năng và hiệu năng chuyên nghiệp hơn.'),
       (8,'MacBook Pro 14 inch M3',8,39999000,'https://res.cloudinary.com/dipiog2a2/image/upload/v1730389217/mbp14-spacegray-select-202310_zgd14n.jpg','macbook-pro-14-inch-m3','MacBook Pro chạy cực nhanh với chip M3, M3 Pro và M3 Max. Được thiết kế trên công nghệ 3 nanômét và sở hữu kiến trúc GPU hoàn toàn mới, đây là dòng chip tiên tiến nhất từng được thiết kế cho máy tính cá nhân. Và mỗi mẫu chip trong đó đều mang đến nhiều tính năng và hiệu năng chuyên nghiệp hơn.');
