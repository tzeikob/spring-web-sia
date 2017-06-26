INSERT INTO users VALUES (1, 'user1', '$2a$04$P2Df/FMiWh4DncmXHWWbieEWFNr0Ca1I7ejqUMA84btNtqyJI/tmS', true);
INSERT INTO users VALUES (2, 'user2', '$2a$04$P2Df/FMiWh4DncmXHWWbieEWFNr0Ca1I7ejqUMA84btNtqyJI/tmS', true);

INSERT INTO roles VALUES (1, 1, 'USER');
INSERT INTO roles VALUES (2, 1, 'ADMIN');
INSERT INTO roles VALUES (3, 2, 'USER');

INSERT INTO persons VALUES (1, 'bob');
INSERT INTO persons VALUES (2, 'alice');

INSERT INTO items VALUES(1, 1, 'item 1', 'lorem ipsum', '2017-01-02');
INSERT INTO items VALUES(2, 1, 'item 2', 'lorem ipsum', '2017-02-12');
INSERT INTO items VALUES(3, 2, 'item 3', 'lorem ipsum', '2017-02-14');
INSERT INTO items VALUES(4, 2, 'item 4', 'lorem ipsum', '2017-02-07');
