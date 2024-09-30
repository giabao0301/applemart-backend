CREATE DATABASE cart;
USE cart;

CREATE TABLE cart (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL
);

create table cart_item (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cart_id INT NOT null,
    product_item_id INT NOT NULL,
    quantity INT,
    FOREIGN KEY(cart_id) REFERENCES cart(id)
)