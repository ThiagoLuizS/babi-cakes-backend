create table if not exists tb_address
(
    id_address      int auto_increment primary key not null,
    id_user         int                            not null,
    adds_main       bool default false,
    adds_cep        varchar(50)                    not null,
    adds_type       varchar(50)                    not null,
    adds_name       varchar(250)                   not null,
    adds_state      varchar(250)                   not null,
    adds_district   varchar(250)                   not null,
    adds_lat        varchar(250)                   not null,
    adds_lng        varchar(250)                   not null,
    adds_city       varchar(250)                   not null,
    adds_city_ibge  varchar(250)                   not null,
    adds_dd         varchar(250)                   not null,
    adds_number     varchar(50)                    not null,
    adds_complement varchar(250)
);

alter table tb_address
    ADD CONSTRAINT tb_address_user_fkey FOREIGN KEY (id_user) REFERENCES tb_user (id_user);
