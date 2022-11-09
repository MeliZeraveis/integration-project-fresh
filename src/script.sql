USE meli_fresh;

INSERT INTO warehouse VALUES (1, 'MLBSP01' 5),
                             (2, 'MLBSP02' 5),
                             (3, 'MLARBA01' 6);

INSERT INTO user VALUES (1, 'JonatasBene', '1234', 'jonatasbene@email.com', 'buyer'),
                         (2, 'BrunaCampos', '1234', 'brunacampos@email.com', 'buyer'),
                        (3, 'RosySantos', '5678', 'rosysantos@email.com', 'seller'),
                        (4, 'EstherPorto', '5678', 'estherporto@email.com', 'seller'),
                        (5, 'MarcelloAlves', '9090', 'marcelloalves@email.com', 'manager'),
                        (6, 'HugoCaxias', '9090', 'hugocaxias@email.com', 'manager');

INSERT INTO section VALUES (1, 'Fresco'),
                           (2, 'Fresco'),
                           (3, 'Refrigerado'),
                           (4, 'Refrigerado'),
                           (5, 'Congelado'),
                           (6, 'Congelado');

INSERT INTO announcement VALUES (1, 'Maçã', 'descricao', 3),
                           (2, 'Banana', 'descricao', 3),
                           (3, 'Sorvete', 'descricao', 4),
                           (4, 'Carne Moida', 'descricao', 4),
                           (5, 'Manteiga', 'descricao', 3),
                           (6, 'Requeijão', 'descricao', 4),
                           (7, 'Cereja', 'descricao', 3),
                           (8, 'Queijo', 'descricao', 3),
                           (9, 'Camarão', 'descricao', 4);

INSERT INTO batch VALUES (1, 1, 17.0, "2022-12-30", "2022-08-13", "2020-08-13 17:55:00", 1, 10.0, 2, 2.5),
                         (2, 2, 18.0, "2022-12-30", "2022-08-13", "2020-08-13 17:55:00", 1, 20.0, 2, 3.5),
                         (3, 3, 1.0, "2023-12-31", "2022-08-13", "2020-08-13 17:55:00", 1, 1.0, 2, 4.0),
                         (4, 4, 2.0, "2023-02-30", "2022-08-13", "2020-08-13 17:55:00", 2, 0.0, 3, 3.0),
                         (5, 5, 10.0, "2023-11-25", "2022-08-13", "2020-08-13 17:55:00", 2, 15.0, 3, 1.0),
                         (6, 6, 13.0, "2023-01-05", "2022-08-13", "2020-08-13 17:55:00", 2, 8.0, 3, 1.0),
                         (7, 7, 13.0, "2022-12-30", "2022-08-13", "2020-08-13 17:55:00", 3, 12.0, 1, 1.0),
                         (8, 8, 10.0, "2022-12-30", "2022-08-13", "2020-08-13 17:55:00", 4, 15.0, 1, 1.0),
                         (9, 9, 3.0, "2023-01-15", "2022-08-13", "2020-08-13 17:55:00", 4, 5.0, 2, 2.5);
