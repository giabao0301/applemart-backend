CREATE DATABASE `order`;
USE `order`;

CREATE TABLE shipping_method (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL
);

CREATE TABLE payment_method (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    provider VARCHAR(255),
    account_number VARCHAR(50),
    expiry_date DATE,
    is_default BIT(1)
);

CREATE TABLE `order` (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    order_date DATE NOT NULL,
    payment_method_id INT NOT NULL,
    payment_status VARCHAR(50) NOT NULL,
    address_id INT NOT NULL,
    total_amount DOUBLE NOT NULL,
    shipping_method_id INT NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    FOREIGN KEY (payment_method_id) REFERENCES payment_method(id),
    FOREIGN KEY (shipping_method_id) REFERENCES shipping_method(id)
);

CREATE TABLE order_line (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	order_id INT NOT NULL,
	product_item_id INT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `order`(id)
);

