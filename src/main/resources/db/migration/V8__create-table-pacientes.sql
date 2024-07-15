
create table  pacientes(

        id serial primary key ,
        nombre varchar(100) not null,
        email varchar(100) not null unique ,
        documento varchar(20) not null unique,
        calle varchar(100) not null,
        distrito varchar(100) not null,
        complemento varchar(100) not null,
        numero varchar(20),
        ciudad varchar(100) not null,
        telefono varchar(20) not null,
        activo smallint not null
)