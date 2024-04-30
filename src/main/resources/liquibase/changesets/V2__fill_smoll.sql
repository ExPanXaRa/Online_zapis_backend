INSERT INTO "black_lists" ("master_id", "cleints_phone")
VALUES (1, '+314534564');
INSERT INTO "black_lists" ("master_id", "cleints_phone")
VALUES (2, '+23453246');
INSERT INTO "black_lists" ("master_id", "cleints_phone")
VALUES (3, '+32453245');
INSERT INTO "black_lists" ("master_id", "cleints_phone")
VALUES (4, '+34564356');
INSERT INTO "black_lists" ("master_id", "cleints_phone")
VALUES (5, '+23452345234');


INSERT INTO "orders" ("cliend_id", "master_id", "price", "time_of_create",
                      "time_of_start", "time_of_end", "rating")
VALUES (1, 2, 2134.00, CURRENT_TIMESTAMP, '2024-04-05 10:00:00',
        '2024-04-05 12:00:00', 5);
INSERT INTO "orders" ("cliend_id", "master_id", "price", "time_of_create",
                      "time_of_start", "time_of_end", "rating")
VALUES (2, 3, 523.00, CURRENT_TIMESTAMP, '2024-04-05 10:00:00',
        '2024-04-05 12:00:00', 3);
INSERT INTO "orders" ("cliend_id", "master_id", "price", "time_of_create",
                      "time_of_start", "time_of_end", "rating")
VALUES (3, 1, 243.00, CURRENT_TIMESTAMP, '2024-04-05 10:00:00',
        '2024-04-05 12:00:00', 1);
INSERT INTO "orders" ("cliend_id", "master_id", "price", "time_of_create",
                      "time_of_start", "time_of_end", "rating")
VALUES (1, 2, 1234.00, CURRENT_TIMESTAMP, '2024-04-05 10:00:00',
        '2024-04-05 12:00:00', 2);
INSERT INTO "orders" ("cliend_id", "master_id", "price", "time_of_create",
                      "time_of_start", "time_of_end", "rating")
VALUES (2, 2, 423.00, CURRENT_TIMESTAMP, '2024-04-05 10:00:00',
        '2024-04-05 12:00:00', 3);



INSERT INTO "sale_cards" ("master_id", "name", "percent")
VALUES (1, 'Скидка ыфваыфвна услуги', 2.00);
INSERT INTO "sale_cards" ("master_id", "name", "percent")
VALUES (2, 'Скидка рна услуги', 3.00);
INSERT INTO "sale_cards" ("master_id", "name", "percent")
VALUES (2, 'Скфываывидка на услуги', 4.00);
INSERT INTO "sale_cards" ("master_id", "name", "percent")
VALUES (2, 'Скидка на услрвыаруги', 15.00);
INSERT INTO "sale_cards" ("master_id", "name", "percent")
VALUES (3, 'Скиыфвадка на услуги', 2.00);


INSERT INTO "clients_sale_cards" ("client_id", "sale_card_id")
VALUES (1, 1);
INSERT INTO "clients_sale_cards" ("client_id", "sale_card_id")
VALUES (2, 2);
INSERT INTO "clients_sale_cards" ("client_id", "sale_card_id")
VALUES (1, 2);


INSERT INTO "services" ("master_id", "name", "comment", "price",
                        "standart_time")
VALUES (1, 'Услуга 42', 'Комментарий к услуге', 345234.00, '01:00:00');
INSERT INTO "services" ("master_id", "name", "comment", "price",
                        "standart_time")
VALUES (1, 'Услуга 123', 'Комментарий к услуге', 645.00, '03:00:00');
INSERT INTO "services" ("master_id", "name", "comment", "price",
                        "standart_time")
VALUES (2, 'Услуга 1234', 'Комментарий к услуге', 2314.00, '02:02:00');
INSERT INTO "services" ("master_id", "name", "comment", "price",
                        "standart_time")
VALUES (3, 'Услуга 5324', 'Комментарий к услуге', 65.00, '02:04:00');
INSERT INTO "services" ("master_id", "name", "comment", "price",
                        "standart_time")
VALUES (4, 'Услуга 423', 'Комментарий к услуге', 234.00, '01:03:00');

INSERT INTO "orders_services" ("order_id", "service_id")
VALUES (1, 3);
INSERT INTO "orders_services" ("order_id", "service_id")
VALUES (2, 4);
INSERT INTO "orders_services" ("order_id", "service_id")
VALUES (3, 1);
INSERT INTO "orders_services" ("order_id", "service_id")
VALUES (3, 2);
INSERT INTO "orders_services" ("order_id", "service_id")
VALUES (4, 3);
INSERT INTO "orders_services" ("order_id", "service_id")
VALUES (5, 3);

