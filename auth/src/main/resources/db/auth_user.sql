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
    ) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `user` (`id`, `date_of_birth`, `email`, `full_name`, `password`, `phone_number`, `profile_image_url`, `username`, `enabled`)
VALUES (1,'2024-12-30','admin@apple.com','Quản trị viên','$2a$10$FALJmAxdaVstrkIkYNVGUuQbBY96ZOuEnG3oYCv4htV3xXFc24kZ2','0869787482','','admin',_binary ''),
       (32,'2024-12-30','trinhpao246@gmail.com','Trịnh Gia Bảo','$2a$10$/BcEL3yYe1/F5jKs6KUEoeDa1W0kEZdZbNLmnMEXhsTViv6IQDddi','0898778996','','giabao144',_binary ''),
       (33,'2024-12-30','trinhbao135@gmail.com','Nguyễn A Nhú','$2a$10$LC3G2k52o6O7kPdkZXeO.ejQBpCDlGhNf415.vD3515d.romeFb..','0223047323','https://res.cloudinary.com/dipiog2a2/image/upload/v1735541247/asyotegzrhlbhenaleb4.png','user',_binary ''),
       (34,'2025-01-04','g2weebd@gmail.com','Nguyen Van B','$2a$10$ppMpofGZ8276ZapkHHnbEO6A1/JjViK/WaTAyND0.otItxaVD1062','0876554334','https://res.cloudinary.com/dipiog2a2/image/upload/v1735951639/me5erzo5v7bkfftk8pv1.png','user123',_binary ''),
       (40,'2025-01-04','21521866@gm.uit.edu.vn','Trịnh Gia Bảo','$2a$10$MAh0GsTkCQB4PW2Z2LxSl.LvQiqt0gC7.50WAZknZzWBYESN8pHDi','0223047323','https://res.cloudinary.com/dipiog2a2/image/upload/v1735953847/xvfsi4tf3evts2khbqpp.jpg','giabao123',_binary '');