SET foreign_key_checks = 0;

DELETE FROM city;
DELETE FROM `group`;
DELETE FROM group_permission;
DELETE FROM kitchen;
DELETE FROM `order`;
DELETE FROM order_item;
DELETE FROM payment_method;
DELETE FROM permission;
DELETE FROM product;
DELETE FROM restaurant;
DELETE FROM restaurant_payment_method;
DELETE FROM restaurant_product;
DELETE FROM state;
DELETE FROM user;
DELETE FROM user_group;

SET foreign_key_checks = 1;

ALTER TABLE city AUTO_INCREMENT = 1;
ALTER TABLE `group` AUTO_INCREMENT = 1;
ALTER TABLE kitchen AUTO_INCREMENT = 1;
ALTER TABLE `order` AUTO_INCREMENT = 1;
ALTER TABLE payment_method AUTO_INCREMENT = 1;
ALTER TABLE permission AUTO_INCREMENT = 1;
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE restaurant AUTO_INCREMENT = 1;
ALTER TABLE state AUTO_INCREMENT = 1;
ALTER TABLE user AUTO_INCREMENT = 1;

INSERT INTO kitchen (id, name) VALUES (1, 'Tailandesa');
INSERT INTO kitchen (id, name) VALUES (2, 'Indiana');
INSERT INTO kitchen (id, name) VALUES (3, 'Argentina');
INSERT INTO kitchen (id, name) VALUES (4, 'Brasileira');

INSERT INTO state (id, name) VALUES (1, 'Minas Gerais');
INSERT INTO state (id, name) VALUES (2, 'São Paulo');
INSERT INTO state (id, name) VALUES (3, 'Ceará');

INSERT INTO city (id, name, state_id) VALUES (1, 'Uberlândia', 1);
INSERT INTO city (id, name, state_id) VALUES (2, 'Belo Horizonte', 1);
INSERT INTO city (id, name, state_id) VALUES (3, 'São Paulo', 2);
INSERT INTO city (id, name, state_id) VALUES (4, 'Campinas', 2);
INSERT INTO city (id, name, state_id) VALUES (5, 'Fortaleza', 3);

INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active, address_city_id, address_zipcode, address_street, address_number, address_district) VALUES (1, 'Thai Gourmet', 10, 1, UTC_TIMESTAMP(), UTC_TIMESTAMP(), true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active) VALUES (2, 'Thai Delivery', 9.50, 1, UTC_TIMESTAMP(), UTC_TIMESTAMP(), true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active) VALUES (3, 'Tuk Tuk Comida Indiana', 15, 2, UTC_TIMESTAMP(), UTC_TIMESTAMP(), true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active) VALUES (4, 'Java Steakhouse', 15, 3, UTC_TIMESTAMP(), UTC_TIMESTAMP(), true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active) VALUES (5, 'Lanchonete da Maria', 9.99, 4 ,UTC_TIMESTAMP(), UTC_TIMESTAMP(), true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active) VALUES (6, 'Bar sem Lona', 6, 4.49, UTC_TIMESTAMP(), UTC_TIMESTAMP(), true);

INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (1, 'Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (2, 'Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (3, 'Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (4, 'Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (5, 'Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (6, 'Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (7, 'T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (8, 'Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 5);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (9, 'Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 6);

INSERT INTO payment_method (id, description) VALUES (1, 'Cartão de crédito');
INSERT INTO payment_method (id, description) VALUES (2, 'Cartão de débito');
INSERT INTO payment_method (id, description) VALUES (3, 'Dinheiro');

INSERT INTO permission (id, name, description) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permission (id, name, description) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);