CREATE DATABASE product;
USE product;

CREATE TABLE category (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    url_key VARCHAR(255) NOT NULL,
	thumbnail_url VARCHAR(255),
    parent_category_id INT,
    FOREIGN KEY(parent_category_id) REFERENCES category(id)
);

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    lowest_price DOUBLE NOT NULL, 
    thumbnail_url VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
	description text,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE product_item (
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    product_id	INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    qty_in_stock INT NOT NULL,
    price DOUBLE NOT NULL, 
    image_url VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE product_image (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	product_id INT NOT NULL,
	url VARCHAR(255) NOT NULL,
    FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE variation (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    name VARCHAR(255),
	FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE variation_option (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    variation_id INT NOT NULL,
    value VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    FOREIGN KEY(variation_id) REFERENCES variation(id)
);

CREATE TABLE product_attribute (
	 id INT NOT NULL  PRIMARY KEY AUTO_INCREMENT,
	 product_item_id INT NOT NULL,
     `value` VARCHAR(255) NOT NULL,
     FOREIGN KEY (product_item_id) REFERENCES product_item(id)
);

CREATE TABLE product_configuration (
	product_item_id INT NOT NULL,
    variation_option_id INT NOT NULL,
    FOREIGN KEY(product_item_id) REFERENCES product_item(id),
    FOREIGN KEY(variation_option_id) REFERENCES variation_option(id)
);