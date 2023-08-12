CREATE TABLE IF NOT EXISTS kitchen (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS city (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(80) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS state (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(40) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE city ADD CONSTRAINT IF NOT EXISTS fk_city_state FOREIGN KEY (state_id) REFERENCES state (id);

CREATE TABLE IF NOT EXISTS payment_method (
	id BIGINT NOT NULL AUTO_INCREMENT, 
    description VARCHAR(60) NOT NULL, 
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `group` (
	id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS group_permission (
	group_id BIGINT NOT NULL, 
	permission_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS permission (
	id BIGINT NOT NULL AUTO_INCREMENT, 
    description VARCHAR(60) NOT NULL, 
    name VARCHAR(100) NOT NULL, 
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS product (
	id BIGINT NOT NULL AUTO_INCREMENT, 
    active TINYINT(1) NOT NULL,
    description TEXT NOT NULL, 
    name VARCHAR(80) NOT NULL, 
    price DECIMAL(10,2) NOT NULL, 
    restaurant_id BIGINT NOT NULL, 
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS restaurant (
	id BIGINT NOT NULL AUTO_INCREMENT, 
    update_date datetime NOT NULL, 
    creation_date datetime NOT NULL, 
    address_district VARCHAR(80), 
    address_zipcode VARCHAR(9), 
    address_complement VARCHAR(80), 
    address_street VARCHAR(120), 
    address_number VARCHAR(10), 
    name VARCHAR(80) NOT NULL, 
    shipping_fee DECIMAL(10,2) NOT NULL, 
    kitchen_id BIGINT NOT NULL, 
    address_city_id BIGINT, 
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS restaurant_payment_method (
	restaurant_id BIGINT NOT NULL, 
	payment_method_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user` (
	id BIGINT NOT NULL AUTO_INCREMENT, 
    creation_date datetime NOT NULL, 
    email VARCHAR(100) NOT NULL, 
    name VARCHAR(80) NOT NULL, 
    password VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS user_group (
	user_id BIGINT NOT NULL, 
    group_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE group_permission ADD CONSTRAINT IF NOT EXISTS fk_group_permission_permission FOREIGN KEY (permission_id) REFERENCES permission (id);
ALTER TABLE group_permission ADD CONSTRAINT IF NOT EXISTS fk_group_permission_group FOREIGN KEY (group_id) REFERENCES `group` (id);

ALTER TABLE product ADD CONSTRAINT IF NOT EXISTS fk_product_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE restaurant ADD CONSTRAINT IF NOT EXISTS fk_restaurant_kitchen FOREIGN KEY (kitchen_id) REFERENCES kitchen (id);
ALTER TABLE restaurant ADD CONSTRAINT IF NOT EXISTS fk_restaurant_city FOREIGN KEY (address_city_id) REFERENCES city (id);

ALTER TABLE restaurant_payment_method ADD CONSTRAINT IF NOT EXISTS fk_restaurant_payment_method_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method (id);
ALTER TABLE restaurant_payment_method ADD CONSTRAINT IF NOT EXISTS fk_restaurant_payment_method_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE user_group ADD CONSTRAINT IF NOT EXISTS fk_user_group_group FOREIGN KEY (group_id) REFERENCES `group` (id);
ALTER TABLE user_group ADD CONSTRAINT IF NOT EXISTS fk_user_group_user FOREIGN KEY (user_id) REFERENCES `user` (id);

CREATE TABLE IF NOT EXISTS `order` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    subtotal DECIMAL(10,2) NOT NULL,
    shipping_fee DECIMAL(10,2) NOT NULL,
    total_value DECIMAL(10,2) NOT NULL,
    restaurant_id BIGINT NOT NULL,
    user_client_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    address_city_id BIGINT(20) NOT NULL,
    address_district VARCHAR(80), 
    address_zipcode VARCHAR(9), 
    address_complement VARCHAR(80), 
    address_street VARCHAR(120), 
    address_number VARCHAR(10), 
    status VARCHAR(10) NOT NULL,
    creation_date datetime NOT NULL,
    confirmation_date datetime NULL,
    cancellation_date datetime NULL,
    delivery_date datetime NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_order_address_city FOREIGN KEY (address_city_id) REFERENCES city (id),
    CONSTRAINT fk_order_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id),
    CONSTRAINT fk_order_user_client FOREIGN KEY (user_client_id) REFERENCES `user` (id),
    CONSTRAINT fk_order_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT NOT NULL AUTO_INCREMENT,
    quantity SMALLINT(6) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    note VARCHAR(255) NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_item_product (order_id, product_id),

    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES `order` (id),
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;