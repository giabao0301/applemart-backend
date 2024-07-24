CREATE DATABASE applemart;
DROP database applemart;
USE applemart;


-- Users/Customer Information table
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL, 
    full_name VARCHAR(100),
    date_of_birth DATE,
    phone_number VARCHAR(20)
);

CREATE TABLE role (
	id INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_role (
	user_id INT NOT NULL,
    role_id INT NOT NULL,
    foreign key (user_id) references user(id),
    foreign key (role_id) references role(id)
);

create table address (
	id int auto_increment not null primary key,
    user_id int not null,
	address_line varchar(255) not null,
    ward varchar(255) not null,
    district varchar(255) not null,
    city varchar(255) not null,
    foreign key (user_id) references user(id)
);

CREATE TABLE category (
    id int PRIMARY KEY NOT NULL auto_increment,
    name VARCHAR(255) NOT NULL,
    url_key varchar(255) not null,
	image_url VARCHAR(255) not null
);

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY not null,
    name VARCHAR(100) NOT NULL,
    category_id int not null,
	description text,
    image_url varchar(255) not null,
    slug varchar(255) not null,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

create table product_item(
	id int AUTO_INCREMENT not null primary key,
    product_id	int not null,
    sku varchar(255) not null,
    qty_in_stock int not null,
    price DECIMAL(6,3) NOT NULL, 
    slug varchar(255) not null,
    foreign key(product_id) references product(id)
);

create table product_image (
	id int not null primary key auto_increment,
	product_item_id int not null,
	image_url varchar(255) not null,
    foreign key(product_item_id) references product_item(id)
);

CREATE TABLE product_attribute (
	id int not null  primary key auto_increment,
	 product_item_id INT NOT NULL,
     `key` VARCHAR(50) NOT NULL,
     `value` VARCHAR(200) NOT NULL,
     FOREIGN KEY (product_item_id) REFERENCES product_item(id)
);

create table variation (
	id int not null primary key auto_increment,
    category_id int not null,
    name varchar(255),
    foreign key(category_id) references category(id)
);

create table variation_option (
	id int not null auto_increment primary key,
    variation_id int not null,
    value varchar(255),
    foreign key(variation_id) references variation(id)
);


create table product_configuration (
	product_item_id int not null,
    variation_option_id int not null,
    foreign key(product_item_id) references product_item(id),
    foreign key(variation_option_id) references variation_option(id)
);


-- Orders table
CREATE TABLE `order` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(50),
    delivery_status VARCHAR(50),
    shipping_address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES User(id)
);

-- Order Details table
CREATE TABLE order_detail (
    id INT AUTO_INCREMENT PRIMARY KEY,
	order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2) NOT NULL,
    shipping_fee DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `order`(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Reviews/Ratings table
CREATE TABLE review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    rating INT,
    review_text TEXT,
    date DATE,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);

-- Admins table
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE cart (
	id INT NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    foreign key (user_id) references user(id)
);

create table cart_item (
	id int not null primary key,
    cart_id int not null,
    product_item_id int not null,
    quantity int,
    foreign key(cart_id) references shopping_cart(id),
    foreign key(product_item_id) references product(id)
)
