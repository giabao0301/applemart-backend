DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `profile_image_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user` (`id`, `date_of_birth`, `email`, `full_name`, `password`, `phone_number`, `profile_image_url`, `username`, `enabled`)
VALUES (1,NULL,'admin@applemart.com',NULL,'$2a$10$.ZH9qaicMvQVrP7HPFgQQOWYWPP3vHqIJPJxJpO5th6u/NvztkyJW',NULL,NULL,'admin',_binary ''),
       (2,'2009-12-13','trinhbao135@gmail.com','Nguyen Van B','$2a$10$XT/FdyqZWng2Psy1kZPbWOvLWe4DB4Ch2HhEuPuQqb1fQwSixGY5.','0223047323','','user1',_binary ''),
       (7,'2009-12-13','trinhpao246@gmail.com','Nguyen Van A','$2a$10$XZUX9klG2gWQ4oX39p2ZiOGG3dcn.182a9V9VBtnkkCmD.wLnKahS','0223047323','https://res.cloudinary.com/dipiog2a2/image/upload/v1733036625/xdolk0fixpdxdldgdivj.webp','user2',_binary ''),
       (28,NULL,'21521866@gm.uit.edu.vn','Trịnh Gia Bảo','$2a$10$4IMpFBqjplUX557D/zn1P.TZjtmwS41Plvl2xzCPU93EMdJycRYrW',NULL,NULL,'giabao144',_binary ''),
       (30,NULL,'g2weebd@gmail.com','Trịnh Gia Bảo','$2a$10$FBud0ww7SwOOER32G4Hig.M5.8DoKEUBbcN4nKD9Ps7P2gXOPHRqu',NULL,NULL,'giabao1',_binary '\0');