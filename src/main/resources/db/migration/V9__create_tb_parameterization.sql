create table if not exists tb_parameterization
(
    id_param              int auto_increment primary key not null,
    param_freight_cost    decimal(10, 2)                 not null,
    param_pix_api_key     text,
    param_pix_client_key  text,
    param_pix_account_key text,
    param_city_limit      text
);

insert into tb_parameterization(param_freight_cost)
values (4.00);

