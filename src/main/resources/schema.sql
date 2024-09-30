-- Primero se deben borrar todas las tablas (de detalle a maestro) y luego añadirlas (de maestro a detalle)
-- (en este caso en cada aplicación se usa solo una tabla, por lo que no hace falta)

-- Para giis.demo.tkrun:
drop table if exists Carreras;
create table Carreras (
    id int primary key not null, 
    inicio date not null, 
    fin date not null, 
    fecha date not null, 
    descr varchar(32), 
    check (inicio <= fin), 
    check (fin < fecha)
);
