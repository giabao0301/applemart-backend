DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url_key` varchar(255) NOT NULL,
  `thumbnail_url` varchar(255) DEFAULT NULL,
  `parent_category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url_key_UNIQUE` (`url_key`),
  KEY `parent_category_id` (`parent_category_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`parent_category_id`) REFERENCES `category` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 13 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `
    category`
VALUES (1, 'Mac', 'mac', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303857/store-card-13-mac-nav-202310_eek6sw.png', NULL),
       (2, 'iPhone', 'iphone', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303883/store-card-13-iphone-nav-202409_xccjh9.png', NULL),
       (3, 'iPad', 'ipad', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730303925/store-card-13-ipad-nav-202405_iuymkf.png', NULL),
       (4, 'Apple Watch', 'apple-watch', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304156/store-card-13-watch-nav-202409_ktwnyj.png', NULL),
       (5, 'AirPods', 'airpods', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304256/store-card-13-airpods-nav-202409_sozcmb.png', NULL),
       (6, 'Phụ Kiện', 'phu-kien', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304268/store-card-13-accessories-nav-202409_mew5to.png', NULL),
       (7, 'MacBook Air', 'macbook-air', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304284/mac-card-40-macbook-air-m2-m3-202402_ru2q9h.jpg', 1),
       (8, 'MacBook Pro', 'macbook-pro', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304300/mac-card-40-macbookpro-14-16-202310_qo9tch.jpg', 1),
       (9, 'iMac', 'imac', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304315/mac-card-40-imac-24-202310_n6ssra.jpg', 1),
       (10, 'Mac mini', 'mac-mini', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304329/mac-card-40-mac-mini-202301_c6utbt.jpg', 1),
       (11, 'Studio Display', 'studio-display', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304341/mac-card-40-studio-display-202203_etlef8.jpg', 1),
       (12, 'Pro Display XDR', 'pro-display-xdr', 'https://res.cloudinary.com/dipiog2a2/image/upload/v1730304355/mac-card-40-mac-pro-display-202108_fbqsir.jpg', 1);