drop database if exists cuentas_bancarias;
create database cuentas_bancarias;
use cuentas_bancarias;

-- DROP ENTIDADES
drop table if exists movimientos;
drop table if exists cuentas;
drop table if exists clientes;

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
    contrasenia nvarchar(255) not null,
    estado bool not null default '1'
);

-- una persona puede estar registrada una única vez
create unique index idx_identificacion on clientes(identificacion);

create table cuentas(
	cuenta_id int not null auto_increment primary key,
    numero nvarchar(20) not null, -- una secuencia
    tipo_cuenta nvarchar(10) not null,
    saldo_inicial decimal(10,2) not null,
    estado bool default '1',
    cliente_id int not null,
    foreign key (cliente_id) references clientes(cliente_id)
);

-- la combinación de numero y tipo es única
create unique index idx_cuenta on cuentas(numero, tipo_cuenta);

create table movimientos(
	movimiento_id int not null auto_increment primary key,
    fecha date not null,
    tipo_movimiento nvarchar(10) not null,
    valor decimal(10,2) not null, -- positivos o negativos
    saldo decimal(10,2) not null,
    cuenta_id int not null,
    foreign key (cuenta_id) references cuentas(cuenta_id)
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
        Q.tipo_cuenta as tipoCuenta, 
        Q.saldo_inicial as saldoInicial, 
        Q.estado as estado, 
        M.valor as movimiento, 
        M.saldo as saldoDisponible
    from clientes C
    inner join cuentas Q on Q.cliente_id = C.cliente_id
    inner join movimientos M on M.cuenta_id = Q.cuenta_id
    where M.fecha between I_FECHA_INICIO and I_FECHA_FIN
    and Q.cliente_id = I_CLIENTE_ID
    order by M.fecha desc;
    
end block1;;
delimiter ;

-- call sp_reporte('2022-02-13','2022-02-13',2);

/*
-- ---------------------------------------------------------------------------------------
-- QUERIES
-- ---------------------------------------------------------------------------------------

select * from clientes;

select * from cuentas;

select * from movimientos;
*/