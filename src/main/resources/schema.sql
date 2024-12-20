DROP SCHEMA PUBLIC CASCADE;

CREATE TABLE empleados (
    id_empleado INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    tipo_empleado VARCHAR(20) NOT NULL,
    tipo_detalle VARCHAR(50) NOT NULL,
    salario_anual_bruto DECIMAL(15, 2) NOT NULL,
    CHECK (fecha_nacimiento <= CURRENT_DATE)
);

CREATE TABLE horarios (
    id_horario INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_empleado INTEGER NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    es_semanal BOOLEAN NOT NULL,
    dia_semana INTEGER, 
    fecha_especifica DATE,
    FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
);

CREATE TABLE campañas (
    id_campaña INTEGER PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    fase integer NOT NULL,
    numeroAcciones INTEGER NOT NULL,
    activa BOOLEAN
);

CREATE TABLE accionistas (
    id_accionista INTEGER PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    email VARCHAR(100),
);

CREATE TABLE acciones (
    id_accion INTEGER PRIMARY KEY,
    id_campaña INTEGER NOT NULL,
    id_accionista INTEGER NOT NULL,
    en_venta BOOLEAN,
    FOREIGN KEY (id_accionista) REFERENCES accionistas(id_accionista),
    FOREIGN KEY (id_campaña) REFERENCES campañas(id_campaña)
);

CREATE TABLE accionista_campaña (
    id_accionista INTEGER,
    id_campaña INTEGER,
    max_acciones INTEGER NOT NULL,
    PRIMARY KEY (id_accionista, id_campaña),
    FOREIGN KEY (id_accionista) REFERENCES accionistas(id_accionista),
    FOREIGN KEY (id_campaña) REFERENCES campañas(id_campaña),
);

CREATE TABLE Producto (
    id_producto INT PRIMARY KEY IDENTITY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(255),
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Vendedor (
    id_vendedor INT PRIMARY KEY IDENTITY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(100) NOT NULL,
    telefono VARCHAR(20)
);

CREATE TABLE Compra (
    id_compra INT PRIMARY KEY IDENTITY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_vendedor INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_vendedor) REFERENCES Vendedor(id_vendedor)
);

CREATE TABLE Compra_Detalle (
    id_detalle INT PRIMARY KEY IDENTITY,
    id_compra INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
	precio_total DECIMAL(10, 2) GENERATED ALWAYS AS (cantidad * precio_unitario),
    FOREIGN KEY (id_compra) REFERENCES Compra(id_compra),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

CREATE TABLE Venta (
    id_venta INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo VARCHAR(50) NOT NULL,
    total DECIMAL(15, 2) NOT NULL,
    PRIMARY KEY (id_venta)
);


