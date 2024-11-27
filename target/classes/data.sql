
INSERT INTO empleados (id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto)
VALUES 
(2, 'Luis', 'Martínez', '23456789B', '600234567', DATE '1985-03-15', 'No Deportivo', 'Recepcionista', 22000.00);

INSERT INTO empleados (id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto)
VALUES 
(3, 'Carlos', 'Pérez', '34567890C', '600345678', DATE '1992-07-10', 'Deportivo', 'Entrenador', 28000.00);

INSERT INTO empleados (id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto)
VALUES 
(4, 'María', 'López', '45678901D', '600456789', DATE '1988-11-05', 'No Deportivo', 'Contable', 27000.00);

INSERT INTO empleados (id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto)
VALUES 
(5, 'Elena', 'Sánchez', '56789012E', '600567890', DATE '1995-02-28', 'Deportivo', 'Monitora', 25000.00);

INSERT INTO empleados (id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto)
VALUES 
(6, 'Javier', 'Gómez', '67890123F', '600678901', DATE '1980-09-22', 'No Deportivo', 'Mantenimiento', 24000.00);

INSERT INTO empleados (id_empleado, nombre, apellido, dni, telefono, fecha_nacimiento, tipo_empleado, tipo_detalle, salario_anual_bruto)
VALUES 
(7, 'Laura', 'Fernández', '78901234G', '600789012', DATE '1993-12-12', 'Deportivo', 'Socorrista', 23000.00);


INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(2, TIME '08:00:00', TIME '16:00:00', TRUE, 2, NULL); 

INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(2, TIME '09:00:00', TIME '17:00:00', TRUE, 4, NULL); 

INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(2, TIME '10:00:00', TIME '18:00:00', FALSE, NULL, DATE '2023-12-31');

INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(4, TIME '10:00:00', TIME '18:00:00', TRUE, 3, NULL); 

INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(4, TIME '08:00:00', TIME '16:00:00', TRUE, 5, NULL); 

INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(4, TIME '12:00:00', TIME '20:00:00', FALSE, NULL, DATE '2023-11-25');


INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(6, TIME '07:00:00', TIME '15:00:00', TRUE, 1, NULL);

INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(6, TIME '07:00:00', TIME '15:00:00', TRUE, 3, NULL); 

INSERT INTO horarios (id_empleado, hora_inicio, hora_fin, es_semanal, dia_semana, fecha_especifica)
VALUES 
(6, TIME '07:00:00', TIME '15:00:00', TRUE, 5, NULL);

INSERT INTO campañas (id_campaña, nombre, fase, numeroAcciones, activa) VALUES
(1, 'Campaña Primavera', 3, 3, TRUE),
(2, 'Campaña Verano', 2, 5, FALSE),
(3, 'Campaña Otoño', 1, 4, FALSE);

INSERT INTO accionistas (id_accionista, nombre, dni, telefono, email) VALUES
(1, 'Ana López', '12345678A', '123456789', 'ana@example.com'),
(2, 'Carlos Martínez', '23456789B', '234567890', 'carlos@example.com'),
(3, 'Laura García', '34567890C', '345678901', 'laura@example.com'),
(4, 'Juan Rodríguez', '45678901D', '456789012', 'juan@example.com'),
(5, 'Marta Fernández', '56789012E', '567890123', 'marta@example.com');

INSERT INTO acciones (id_accion, id_campaña, id_accionista, en_venta) VALUES
(1, 1, 1, TRUE),
(2, 1, 2, FALSE),
(3, 1, 3, TRUE),
(4, 1, 4, FALSE),
(5, 1, 5, TRUE);

INSERT INTO Vendedor (nombre, dni, telefono) VALUES ('Juan Pérez', '12345678A', '555-1234');
INSERT INTO Producto (nombre, tipo, precio) VALUES
('Camiseta Oficial', 'Ropa', 19.99),
('Gorra Personalizada', 'Accesorios', 12.50),
('Taza con Logo', 'Hogar', 8.75),
('Póster Edición Limitada', 'Decoración', 15.00),
('Llavero Metálico', 'Accesorios', 4.50),
('Sudadera con Capucha', 'Ropa', 29.99),
('Mochila Deportiva', 'Accesorios', 24.99),
('Bolígrafo Corporativo', 'Papelería', 2.00),
('Calendario Anual', 'Papelería', 6.00),
('Pegatina Vinilo', 'Accesorios', 1.50),
('Botella Reutilizable', 'Hogar', 10.00),
('Agenda de Notas', 'Papelería', 7.50),
('Polo Bordado', 'Ropa', 22.00),
('Fundas para Móvil', 'Tecnología', 9.99),
('Auriculares Inalámbricos', 'Tecnología', 35.00);


INSERT INTO Venta (id_venta, fecha, tipo, total) VALUES
(1, '2023-10-01 10:15:00', 'entradas', 150.00),
(2, '2023-10-02 14:30:00', 'abonos de temporada', 1200.00),
(3, '2023-10-03 09:45:00', 'reserva instalaciones deportivas', 300.00),
(4, '2023-10-04 16:20:00', 'campañas de accionistas', 5000.00),
(5, '2023-10-05 11:10:00', 'entradas', 200.00),
(6, '2023-10-06 13:50:00', 'abonos de temporada', 800.00),
(7, '2023-10-07 08:30:00', 'reserva instalaciones deportivas', 450.00),
(8, '2023-10-08 17:25:00', 'campañas de accionistas', 7000.00),
(9, '2023-10-09 12:05:00', 'entradas', 175.00),
(10, '2023-10-10 15:40:00', 'abonos de temporada', 950.00);

