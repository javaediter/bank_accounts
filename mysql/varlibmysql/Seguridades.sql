drop database if exists cuentas_bancarias_seguridades;
create database cuentas_bancarias_seguridades;
use cuentas_bancarias_seguridades;

-- DROP ENTIDADES
drop table if exists usuarios_roles;
drop table if exists usuarios;
drop table if exists roles;

create table usuarios(
	usuario_id int not null auto_increment primary key,
    alias nvarchar(100) not null,
    contrasenia nvarchar(255) not null,
    activo bit
);

create table roles(
	rol_id int not null auto_increment primary key,
    nombre nvarchar(20) not null
);

create table usuarios_roles(
	id int not null auto_increment primary key,
    usuario_id int not null,
    rol_id int not null
);

insert into roles(nombre) values('ROLE_USER');
insert into roles(nombre) values('ROLE_ADMIN');
insert into roles(nombre) values('ROLE_SUPER_ADMIN');

-- cada rol es unico
-- create unique index idx_rol_nombre on roles(nombre);
-- alter table roles drop index idx_rol_nombre;

insert into usuarios(alias, contrasenia, activo) values('admin', '$2a$10$Rf4veGdAqnGLiWa5I0AgreGtWNlYpC2mERzMj4r1vjN16jLw2dFAq', '1'); -- contrasenia= 'guest'
insert into usuarios_roles(usuario_id, rol_id) values(1,2);
