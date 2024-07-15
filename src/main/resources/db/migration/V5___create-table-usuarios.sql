create table usuarios
(
    id  serial primary key,
    nombre varchar(100) not null,
    email  varchar(100) not null
)