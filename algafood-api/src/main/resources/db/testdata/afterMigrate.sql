SET foreign_key_checks = 0;

LOCK TABLES city write, `group` write, group_permission write, kitchen write, `order` write, order_item write, payment_method write, permission write, product write, product_photo write, restaurant write, restaurant_payment_method write, state write, `user` write, user_group write, restaurant_responsible_user write, oauth2_registered_client write;

DELETE FROM city;
DELETE FROM `group`;
DELETE FROM group_permission;
DELETE FROM kitchen;
DELETE FROM `order`;
DELETE FROM order_item;
DELETE FROM payment_method;
DELETE FROM permission;
DELETE FROM product;
DELETE FROM product_photo;
DELETE FROM restaurant;
DELETE FROM restaurant_payment_method;
DELETE FROM state;
DELETE FROM `user`;
DELETE FROM user_group;
DELETE FROM restaurant_responsible_user;
DELETE FROM oauth2_registered_client;

SET foreign_key_checks = 1;

ALTER TABLE city AUTO_INCREMENT = 1;
ALTER TABLE `group` AUTO_INCREMENT = 1;
ALTER TABLE kitchen AUTO_INCREMENT = 1;
ALTER TABLE `order` AUTO_INCREMENT = 1;
ALTER TABLE order_item AUTO_INCREMENT = 1;
ALTER TABLE payment_method AUTO_INCREMENT = 1;
ALTER TABLE permission AUTO_INCREMENT = 1;
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE restaurant AUTO_INCREMENT = 1;
ALTER TABLE `state` AUTO_INCREMENT = 1;
ALTER TABLE `user` AUTO_INCREMENT = 1;

INSERT INTO kitchen (id, name) VALUES (1, 'Tailandesa');
INSERT INTO kitchen (id, name) VALUES (2, 'Indiana');
INSERT INTO kitchen (id, name) VALUES (3, 'Argentina');
INSERT INTO kitchen (id, name) VALUES (4, 'Brasileira');

INSERT INTO `state` (id, name) VALUES (1, 'Minas Gerais');
INSERT INTO `state` (id, name) VALUES (2, 'São Paulo');
INSERT INTO `state` (id, name) VALUES (3, 'Ceará');

INSERT INTO city (id, name, state_id) VALUES (1, 'Uberlândia', 1);
INSERT INTO city (id, name, state_id) VALUES (2, 'Belo Horizonte', 1);
INSERT INTO city (id, name, state_id) VALUES (3, 'São Paulo', 2);
INSERT INTO city (id, name, state_id) VALUES (4, 'Campinas', 2);
INSERT INTO city (id, name, state_id) VALUES (5, 'Fortaleza', 3);

INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active, opened, address_city_id, address_zipcode, address_street, address_number, address_district) VALUES (1, 'Thai Gourmet', 10, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active, opened) VALUES (2, 'Thai Delivery', 9.50, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active, opened) VALUES (3, 'Tuk Tuk Comida Indiana', 15, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active, opened) VALUES (4, 'Java Steakhouse', 15, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active, opened) VALUES (5, 'Lanchonete da Maria', 9.99, 4 ,CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, true);
INSERT INTO restaurant (id, name, shipping_fee, kitchen_id, creation_date, update_date, active, opened) VALUES (6, 'Bar sem Lona', 6, 4.49, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, true);

INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (1, 'Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 0, 1);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (2, 'Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (3, 'Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (4, 'Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (5, 'Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (6, 'Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (7, 'T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (8, 'Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 5);
INSERT INTO product (id, name, description, price, active, restaurant_id) VALUES (9, 'Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 6);

INSERT INTO payment_method (id, description, update_date) VALUES (1, 'Cartão de crédito', CURRENT_TIMESTAMP());
INSERT INTO payment_method (id, description, update_date) VALUES (2, 'Cartão de débito', CURRENT_TIMESTAMP());
INSERT INTO payment_method (id, description, update_date) VALUES (3, 'Dinheiro', CURRENT_TIMESTAMP());

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO permission (id, name, description) VALUES (1, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
INSERT INTO permission (id, name, description) VALUES (2, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
INSERT INTO permission (id, name, description) VALUES (3, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
INSERT INTO permission (id, name, description) VALUES (4, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
INSERT INTO permission (id, name, description) VALUES (5, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários, grupos e permissões');
INSERT INTO permission (id, name, description) VALUES (6, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários, grupos e permissões');
INSERT INTO permission (id, name, description) VALUES (7, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
INSERT INTO permission (id, name, description) VALUES (8, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
INSERT INTO permission (id, name, description) VALUES (9, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
INSERT INTO permission (id, name, description) VALUES (10, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

INSERT INTO `group` (id, name) VALUES (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

INSERT INTO group_permission (group_id, permission_id) SELECT 1, id FROM permission;

INSERT INTO group_permission (group_id, permission_id) SELECT 2, id FROM permission WHERE name LIKE 'CONSULTAR_%';
INSERT INTO group_permission (group_id, permission_id) SELECT 2, id FROM permission WHERE name = 'EDITAR_RESTAURANTES';

INSERT INTO group_permission (group_id, permission_id) SELECT 3, id FROM permission WHERE name LIKE 'CONSULTAR_%';

INSERT INTO group_permission (group_id, permission_id) SELECT 4, id FROM permission WHERE name LIKE '%_RESTAURANTES' OR name LIKE '%_PRODUTOS';

INSERT INTO `user` (id, name, email, password, creation_date) VALUES (1, 'João da Silva', 'joao.ger@algafood.com', '$2a$12$X4GgWFvO7LdUIx9UOnmANuGhx9JtM9ahxFXM6jyftlFA.6c715fe2', CURRENT_TIMESTAMP()), (2, 'Maria Joaquina', 'maria.vnd@algafood.com', '$2a$12$X4GgWFvO7LdUIx9UOnmANuGhx9JtM9ahxFXM6jyftlFA.6c715fe2', CURRENT_TIMESTAMP()), (3, 'José Souza', 'jose.aux@algafood.com', '$2a$12$X4GgWFvO7LdUIx9UOnmANuGhx9JtM9ahxFXM6jyftlFA.6c715fe2', CURRENT_TIMESTAMP()), (4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '$2a$12$X4GgWFvO7LdUIx9UOnmANuGhx9JtM9ahxFXM6jyftlFA.6c715fe2', CURRENT_TIMESTAMP()), (5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2a$12$X4GgWFvO7LdUIx9UOnmANuGhx9JtM9ahxFXM6jyftlFA.6c715fe2', CURRENT_TIMESTAMP()), (6, 'João', 'joao@gmail.com', '$2a$12$X4GgWFvO7LdUIx9UOnmANuGhx9JtM9ahxFXM6jyftlFA.6c715fe2', CURRENT_TIMESTAMP());

INSERT INTO user_group (user_id, group_id) VALUES (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

INSERT INTO restaurant_responsible_user (restaurant_id, user_id) VALUES (1, 5), (3, 5);

INSERT INTO `order` (id, code, restaurant_id, user_client_id, payment_method_id, address_city_id, address_zipcode, address_street, address_number, address_complement, address_district, status, creation_date, subtotal, shipping_fee, total_value) VALUES (1, 'be99f609-59dc-43a0-8557-8e3d4761aaaa', 1, 6, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CREATED', CURRENT_TIMESTAMP(), 298.90, 10, 308.90);

INSERT INTO order_item (id, order_id, product_id, quantity, unit_price, total_price, note) VALUES (1, 1, 1, 1, 78.9, 78.9, null);
INSERT INTO order_item (id, order_id, product_id, quantity, unit_price, total_price, note) VALUES (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO `order` (id, code, restaurant_id, user_client_id, payment_method_id, address_city_id, address_zipcode, address_street, address_number, address_complement, address_district, status, creation_date, subtotal, shipping_fee, total_value) VALUES (2, '1fbcb039-bcc4-4bd6-9c92-20ad1646e184', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'DELIVERED', CURRENT_TIMESTAMP(), 79, 0, 79);

INSERT INTO order_item (id, order_id, product_id, quantity, unit_price, total_price, note) VALUES (3, 2, 6, 1, 79, 79, 'Ao ponto');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('1', 'algafood-backend', '2022-11-29 18:58:12', '$2a$10$97f9cT/X/htp85ELK8.IhOBpCRHAmn0Z0cYOJVscCj6esvTIFYOrS', NULL, 'Algafood Backend', 'client_secret_basic', 'client_credentials', '', 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('2', 'algafood-web', '2022-11-29 18:58:12', '$2a$10$ku07Df8C0xrgJ.lId5.Cie..VZH4AReQ0wNIKaqvcMlC3MrjT6IF2', NULL, 'Algafood Web', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('3', 'foodanalytics', '2022-11-29 18:58:12', '$2a$10$E5f93hZ5kq97tcZVVUEtru08Eg9KBkziAdyZegNT/cfgJItimzPwW', NULL, 'Food Analytics', 'client_secret_basic', 'authorization_code', 'http://www.foodanalytics.local:8082', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');

UNLOCK TABLES;
