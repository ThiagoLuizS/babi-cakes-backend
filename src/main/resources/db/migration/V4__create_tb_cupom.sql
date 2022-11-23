create table if not exists tb_cupom
(
    id_cupom           int auto_increment primary key not null,
    id_user            int                            not null,
    cupom_code         varchar(50)                    not null,
    cupom_description  varchar(50)                    not null,
    cupom_date_created datetime                       not null,
    cupom_date_expired datetime,
    cupom_status       varchar(50)                    not null,
    cupom_percentage   decimal(5, 4)                   not null
);

alter table tb_cupom
    ADD CONSTRAINT tb_cupom_user_fkey FOREIGN KEY (id_user) REFERENCES tb_user (id_user);
