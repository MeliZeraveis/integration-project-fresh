use meli_fresh;

INSERT INTO `user` VALUES (1, 'jonatasbene@email.com', '1234', 'buyer',  'JonatasBene'),
                          (2, 'brunacampos@email.com', '1234', 'buyer', 'BrunaCampos'),
                          (3, 'rosysantos@email.com', '5678', 'seller', 'RosySantos'),
                          (4, 'estherporto@email.com', '5678', 'seller', 'EstherPorto'),
                          (5, 'marcelloalves@email.com', '9090', 'manager', 'MarcelloAlves'),
                          (6, 'hugocaxias@email.com', '9090', 'manager', 'HugoCaxias');

INSERT INTO warehouse VALUES (1,'Address A','BR', 'MLBSP01', 5),
                             (2,'Address B','BR', 'MLBSP02', 5),
                             (3,'AddressC','AR', 'MLARBA01', 6);

INSERT INTO section VALUES (1,1000,'Fresh', 800),
                           (2,1500, 'Refrigerated', 1000),
                           (3,2000, 'Frozen', 1200);

-- SELECT * FROM meli_fresh.announcement;
INSERT INTO announcement VALUES (1,'descricao', 'Maçã', 2.50, 1 , 3),
                                (2,'descricao', 'Banana', 3.00, 1, 3),
                                (3,'descricao', 'Sorvete', 20.0, 3, 4),
                                (4,'descricao', 'Carne Moida', 40.0, 3, 4),
                                (5,'descricao', 'Manteiga', 5.55, 2, 3),
                                (6,'descricao', 'Requeijão', 6.75, 2, 4),
                                (7,'descricao', 'Cereja', 18.55, 3, 3),
                                (8,'descricao', 'Queijo', 45.70, 2, 3),
                                (9,'descricao', 'Camarão', 80.70, 3, 4);









-- SELECT * FROM meli_fresh.purchase_order;
INSERT INTO purchase_order VALUES (1, 1, '2022-12-30', 'Pending', 150),
                                  (2, 1, '2022-12-30', 'Approved', 450),
                                  (3, 1, '2022-12-30', 'Approved', 300),
                                  (4, 2, '2022-12-30', 'Approved', 150),
                                  (5, 2, '2022-12-30', 'Approved', 320),
                                  (6, 1, '2022-12-30', 'Approved', 450.77),
                                  (7, 2, '2022-12-30', 'Approved', 125.12),
                                  (8, 1, '2022-12-30', 'Approved', 1000.55),
                                  (9, 2, '2022-12-30', 'Approved', 150.88);


-- SELECT * FROM meli_fresh.purchase_order_items;
INSERT INTO purchase_order_items VALUES (1, 1,  807, 10, 1),
                                        (2, 8, 450.7, 10, 2),
                                        (3, 7, 185.5, 10, 3),
                                        (4, 6,  101.25, 15, 4),
                                        (5, 5,  27.75, 5, 5),
                                        (6, 4,  80, 2, 6),
                                        (7, 3,  20, 1, 7),
                                        (8, 2,  12, 4, 8),
                                        (9, 1,  5, 2, 9);