CREATE TABLE IF NOT EXISTS restaurant_responsible_user (
	restaurant_id BIGINT NOT NULL, 
	user_id BIGINT NOT NULL, 
	
	PRIMARY KEY (restaurant_id, user_id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE restaurant_responsible_user ADD CONSTRAINT IF NOT EXISTS fk_restaurant_user_restaurant
FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE restaurant_responsible_user ADD CONSTRAINT IF NOT EXISTS fk_restaurant_user_user
FOREIGN KEY (user_id) REFERENCES `user` (id);