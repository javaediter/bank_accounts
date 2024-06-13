drop database if exists cuentas_bancarias;
create database cuentas_bancarias;
use cuentas_bancarias;

-- DROP ENTIDADES
drop table if exists movimientos;
drop table if exists cuentas;
drop table if exists clientes;

-- DROP CATALOGOS
drop table if exists generos;
drop table if exists estado_clientes;
drop table if exists estado_cuentas;
drop table if exists tipo_cuentas;
drop table if exists tipo_movimientos;

-- ---------------------------------------------------------------------------------------
--  CREACION DE CATALOGOS
-- ---------------------------------------------------------------------------------------
create table estado_clientes(
	estado_cliente_id int not null primary key,
    valor bit
);

insert into estado_clientes(estado_cliente_id, valor) values(1, 1);
insert into estado_clientes(estado_cliente_id, valor) values(2, 0);

create table estado_cuentas(
	estado_cuenta_id int not null primary key,
    valor bit
);

insert into estado_cuentas(estado_cuenta_id, valor) values(1, 1);
insert into estado_cuentas(estado_cuenta_id, valor) values(2, 0);

create table tipo_cuentas(
	tipo_cuenta_id int not null primary key,
    nombre nvarchar(150) not null
);

insert into tipo_cuentas(tipo_cuenta_id, nombre) values(1, 'Ahorros');
insert into tipo_cuentas(tipo_cuenta_id, nombre) values(2, 'Corriente');

create table tipo_movimientos(
	tipo_movimiento_id int not null primary key,
    nombre nvarchar(10) not null
);

insert into tipo_movimientos(tipo_movimiento_id, nombre) values(1, 'Deposito');
insert into tipo_movimientos(tipo_movimiento_id, nombre) values(2, 'Retiro');

-- ---------------------------------------------------------------------------------------
-- CREACION DE ENTIDADES
-- ---------------------------------------------------------------------------------------
create table clientes(
	cliente_id int not null auto_increment primary key,
    nombre nvarchar(150) not null,
    genero nvarchar(15) not null,
    edad int not null,
    identificacion nvarchar(30) not null,
    direccion nvarchar(250) not null,
    telefono nvarchar(15) not null,
    password nvarchar(255) not null,
    estado_cliente_id int not null default 1,
    foreign key (estado_cliente_id) references estado_clientes(estado_cliente_id)
);

create unique index idx_identificacion on clientes(identificacion);

create table cuentas(
	cuenta_id int not null auto_increment primary key,
    numero nvarchar(100) not null, -- calcular una secuencia
    tipo_cuenta_id int not null,
    saldo_inicial decimal(10,2) not null,
    saldo_disponible decimal(10,2) not null,
    estado_cuenta_id int default 1,
    cliente_id int not null,
    foreign key (estado_cuenta_id) references estado_cuentas(estado_cuenta_id),
    foreign key (tipo_cuenta_id) references tipo_cuentas(tipo_cuenta_id),
    foreign key (cliente_id) references clientes(cliente_id) on delete cascade
);

create unique index idx_cuenta on cuentas(numero, tipo_cuenta_id);

create table movimientos(
	movimiento_id int not null auto_increment primary key,
    fecha date not null,
    tipo_movimiento_id int not null,
    valor decimal(10,2) not null, -- positivos o negativos
    saldo_inicial decimal(10,2) not null, -- saldo antes de registrar el movmiento
    saldo_disponible decimal(10,2) not null,
    cuenta_id int not null,
    foreign key (tipo_movimiento_id) references tipo_movimientos(tipo_movimiento_id),
    foreign key (cuenta_id) references cuentas(cuenta_id) on delete cascade
);

-- ---------------------------------------------------------------------------------------
-- SP REPORTE
-- ---------------------------------------------------------------------------------------
drop procedure if exists sp_reporte;

delimiter ;;
create procedure sp_reporte(
	I_FECHA_INICIO DATE, -- entrada
    I_FECHA_FIN DATE, -- entrada
    I_CLIENTE_ID int -- entrada
)
block1:begin
	select 
		M.fecha, 
        C.nombre as cliente, 
        Q.numero as numeroCuenta, 
        (select tc.nombre from tipo_cuentas tc where tipo_cuenta_id = Q.tipo_cuenta_id) as tipoCuenta, 
        M.saldo_inicial as saldoInicial, 
        (select ec.valor from estado_cuentas ec where estado_cuenta_id = Q.estado_cuenta_id) as estado, 
        M.valor as movimiento, 
        M.saldo_disponible as saldoDisponible
    from clientes C
    inner join cuentas Q on Q.cliente_id = C.cliente_id
    inner join movimientos M on M.cuenta_id = Q.cuenta_id
    where M.fecha between I_FECHA_INICIO and I_FECHA_FIN
    and Q.cliente_id = I_CLIENTE_ID
    order by M.fecha;
    
end block1;;
delimiter ;