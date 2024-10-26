
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

INSERT INTO accionistas (id_accionista, nombre, dni, telefono, email)
VALUES 
    (1, 'Juan Pérez', '12345678A', '600123456', 'juan.perez@example.com'),
    (2, 'María López', '87654321B', '600654321', 'maria.lopez@example.com'),
    (3, 'Carlos García', '45678901C', '600987654', 'carlos.garcia@example.com'),
    (4, 'Ana Fernández', '78901234D', '600456789', 'ana.fernandez@example.com'),
    (5, 'Luis Martínez', '23456789E', '600567890', 'luis.martinez@example.com');
