create table if not exists tb_parameterization
(
    id_param           int auto_increment primary key not null,
    param_freight_cost decimal(10, 2)                 not null
);

insert into tb_parameterization(param_freight_cost) values ( 4.00 );

