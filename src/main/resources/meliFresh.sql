use meli_fresh;

INSERT INTO `user` VALUES (1, 'jonatasbene@email.com', '1234', 'buyer',  'JonatasBene'),
                          (2, 'brunacampos@email.com', '1234', 'buyer', 'BrunaCampos'),
                          (3, 'rosysantos@email.com', '5678', 'seller', 'RosySantos'),
                          (4, 'estherporto@email.com', '5678', 'seller', 'EstherPorto'),
                          (5, 'marcelloalves@email.com', '9090', 'manager', 'MarcelloAlves'),
                          (6, 'hugocaxias@email.com', '9090', 'manager', 'HugoCaxias'),
                          (7, 'fulano@email.com', '9080', 'manager', 'FulanoSilva');

INSERT INTO warehouse VALUES (1,'Address A','BR', 'MLBSP01', 5),
                             (2,'Address B','BR', 'MLBSP02', 7),
                             (3,'AddressC','AR', 'MLARBA01', 6),
                             (4,'Address B','BR', 'MLBSP02', 1);

INSERT INTO section VALUES (1,1000,'Fresh', 800, 1),
                           (2,1500, 'Refrigerated', 1000, 1),
                           (3,2000, 'Frozen', 1200, 1),
                           (4, 1000,'Fresh', 800, 2),
                           (5,1500, 'Refrigerated', 1000, 2),
                           (6,2000, 'Frozen', 1200, 2),
                           (7,1000,'Fresh', 800, 3),
                           (8,1500, 'Refrigerated', 1000, 3),
                           (9,2000, 'Frozen', 1200, 3);


INSERT INTO announcement VALUES (1,'descricao', 'Maçã', 2.50, 1 , 3),
                                (2,'descricao', 'Banana', 3.00, 1, 3),
                                (3,'descricao', 'Sorvete', 20.0, 3, 4),
                                (4,'descricao', 'Carne Moida', 40.0, 3, 4),
                                (5,'descricao', 'Manteiga', 5.55, 2, 3),
                                (6,'descricao', 'Requeijão', 6.75, 2, 4),
                                (7,'descricao', 'Cereja', 18.55, 3, 3),
                                (8,'descricao', 'Queijo', 45.70, 2, 3),
                                (9,'descricao', 'Camarão', 80.70, 3, 4);





INSERT INTO inbound_order VALUES (1,'2023-01-25',1,1),
                                 (2,'2023-01-25',1,1),
                                 (3,'2023-01-25',3,2);


INSERT INTO batch_stock VALUES (1, 1,'2022-12-30', '2022-08-13', '2022-08-13 17:55:00', 1, 450, 150,"Fresh", 150),
                               (2, 2,'2022-12-30', '2022-08-13', '2022-08-13 17:55:00', 1, 450, 150, "Fresh", 150),
                               (3, 3, '2023-12-31', '2022-08-13', '2022-08-13 17:55:00', 1, 2000, 100, "Refrigerated", 150),
                               (4, 4, '2023-02-27', '2022-08-13', '2022-08-13 17:55:00', 2, 4000, 100,"Refrigerated", 100),
                               (5, 5, '2023-11-25', '2022-08-13', '2022-08-13 17:55:00', 2, 555, 100, "Frozen", 100),
                               (6, 6, '2023-01-05', '2022-08-13', '2022-08-13 17:55:00', 3, 675, 100, "Frozen", 100);



-- SELECT * FROM meli_fresh.purchase_order;
INSERT INTO purchase_order VALUES (1, 1, '2010-12-30', 'Aberto', 150),
                                  (2, 1, '2010-12-30', 'Finalizado', 450),
                                  (3, 1, '2015-12-30', 'Finalizado', 300),
                                  (4, 2, '2015-12-30', 'Finalizado', 150),
                                  (5, 2, '2017-12-30', 'Finalizado', 320),
                                  (6, 1, '2017-12-30', 'Finalizado', 450.77),
                                  (7, 2, '2018-12-30', 'Finalizado', 125.12),
                                  (8, 1, '2022-11-22', 'Finalizado', 1000.55),
                                  (9, 2, '2022-11-22', 'Finalizado', 150.88),
                                  (10, 2, '2022-11-22', 'Finalizado', 150.88),
                                  (11, 2, '2022-11-22', 'Finalizado', 150.88);


-- SELECT * FROM meli_fresh.purchase_order_items;
INSERT INTO purchase_order_items VALUES (1, 1,  807, 10, 1),
                                        (2, 8, 450.7, 10, 2),
                                        (3, 7, 185.5, 10, 3),
                                        (4, 6,  101.25, 15, 4),
                                        (5, 5,  27.75, 5, 5),
                                        (6, 4,  80, 2, 6),
                                        (7, 3,  20, 1, 7),
                                        (8, 2,  12, 4, 8),
                                        (9, 1,  5, 2, 9),
                                        (10, 1,  5, 2, 10),
                                        (11, 1,  5, 2, 11),
                                        (12, 2,  5, 2, 11),
                                        (13, 2,  5, 2, 10);