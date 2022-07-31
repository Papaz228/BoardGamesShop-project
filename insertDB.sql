INSERT INTO local (short_name, name) VALUES ('Eng', 'English');
INSERT INTO local (short_name, name) VALUES ('Rus', 'Russian');
INSERT INTO product_category (local_id, category_name) VALUES (1, 'StrategyGames');
INSERT INTO product_category (local_id, category_name) VALUES (2, 'Стратегические игры');
INSERT INTO product_category (local_id, category_name) VALUES (1, 'Tactical games');
INSERT INTO product_category (local_id, category_name) VALUES (2, 'Тактические игры');
INSERT INTO product_category (local_id, category_name) VALUES (1, 'Economic Games');
INSERT INTO product_category (local_id, category_name) VALUES (2, 'Экономические игры');
INSERT INTO status (local_id, name) VALUES (1, 'Pending');
INSERT INTO status (local_id, name) VALUES (1, 'Completed');
INSERT INTO status (local_id, name) VALUES (1, 'Abort');
INSERT INTO status (local_id, name) VALUES (2, 'Выполняется');
INSERT INTO status (local_id, name) VALUES (2, 'Успешно');
INSERT INTO status (local_id, name) VALUES (2, 'Отменено');
INSERT INTO country (local_id, name) VALUES (1, 'Kazakhstan');
INSERT INTO country (local_id, name) VALUES (1, 'USA');
INSERT INTO country (local_id, name) VALUES (2, 'Казахстан');
INSERT INTO country (local_id, name) VALUES (2, 'США');
INSERT INTO product (name, description, cost, count, country_id, product_category_id, is_active, photo_url) VALUES ('Манчкин', 'Любимая РПГ', 2500, 4, 3, 2, true, 'http://www.sargona.ru/components/com_virtuemart/shop_image/product/munchkin_101.jpg');
INSERT INTO product (name, description, cost, count, country_id, product_category_id, is_active, photo_url) VALUES ('Runebound', 'Very good strategy', 5000, 10, 2, 1, true, 'https://m.media-amazon.com/images/I/81JaIrVIKfL._AC_SL1000_.jpg');
INSERT INTO product (name, description, cost, count, country_id, product_category_id, is_active, photo_url) VALUES ('Рунебаунд', 'Топовая стратегическая игра', 5000, 2, 4, 2, true, 'https://www.mirf.ru/wp-content/uploads/2016/10/board_box.jpg');

INSERT INTO users (first_name, last_name, birthday, phone_number, email, password, is_admin, is_banned) VALUES ('user1', 'userSurname', '2002-05-06', '87006052002', 'alansun2002@gmail.com', 'c2dddb24a7582413f0de22c682295cce', false, false);
INSERT INTO users (first_name, last_name, birthday, phone_number, email, password, is_admin, is_banned) VALUES ('user2', 'userSurname', '2002-06-07', '87073206040', 'student@gm.com', 'cffe03bc22389ffc1bc8609968f3addf', false, false);
INSERT INTO users (first_name, last_name, birthday, phone_number, email, password, is_admin, is_banned) VALUES ('admin', 'admin', '2000-01-01', '87775555555', 'admin@gmail.com', 'a2787c0adcaefe209513b85067302a42', true, false);

INSERT INTO orders (total_cost, date_start, user_id, status_id) VALUES (5000, '2022-06-20', 1, 5);
INSERT INTO orders (total_cost, date_start, user_id, status_id) VALUES (7200, '2022-06-19', 1, 6);

INSERT INTO order_detail (order_id, product_id, count, cost) VALUES (1, 3, 2, 3600);
INSERT INTO order_detail (order_id, product_id, count, cost) VALUES (2, 3, 1, 5000);
