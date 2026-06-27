INSERT INTO clientes (nombre,apellido,email,create_at) VALUES('Javier', 'Davila', 'jd@mail.com',NOW());
INSERT INTO clientes (nombre,apellido,email,create_at) VALUES('Zuleyma', 'Moreno', 'zm@mail.com',NOW());

INSERT INTO productos (nombre,precio,create_at) VALUES('Pantalla Panasonic', 250, NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES('Notebook Acer Aspire', 450, NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES('Mouse Microsoft', 35, NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES('Teclado Mecánico', 75, NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES('Silla Escritorio', 63, NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES('Escritorio Mediano', 120, NOW());

/* Creamos registros de facturas de ejemplo: */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura de ejemplo 01', 'Obs.', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 3);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 6);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura de ejemplo 02', 'Obs.', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 2, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 2, 6);

/* Creamos usuarios de ejemplo  */
INSERT INTO users (username, password, enabled) VALUES ('javier', '$2a$10$MqB1of76StNbG08wgHRYPOAoM.pkZEKft0QMEdRoKlu6C3xL8maSi', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$RetJOlmDUuOhJjSBKdThhu/2pqPwLchRcg/b5V7ZxcFu1VWLNAWWm', 1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');
