create table if not exists tb_inventory
(
    id_inventory       int auto_increment primary key not null,
    id_product         int                            not null,
    inventory_quantity int                            not null
);

alter table tb_inventory
    ADD CONSTRAINT tb_inventory_product_fkey FOREIGN KEY (id_product) REFERENCES tb_product (id_product);

create table if not exists tb_budget
(
    id_budget             int auto_increment primary key not null,
    id_user               int                            not null,
    id_cupom              int,
    budget_date_create    datetime                       not null,
    budget_date_finalized datetime,
    budget_code           varchar(50)                    not null,
    budget_status         varchar(100)                   not null,
    budget_amount         decimal(10, 2)                 not null default 0.0
);

alter table tb_budget
    ADD CONSTRAINT tb_budget_user_fkey FOREIGN KEY (id_user) REFERENCES tb_user (id_user);

alter table tb_budget
    ADD CONSTRAINT tb_budget_cupom_fkey FOREIGN KEY (id_cupom) REFERENCES tb_cupom (id_cupom);

create table if not exists tb_budget_product_reserved
(
    id_bpr       int auto_increment primary key not null,
    id_budget    int                            not null,
    id_product   int                            not null,
    bpr_quantity int                            not null
);

alter table tb_budget_product_reserved
    ADD CONSTRAINT tb_budget_product_reserved_budget_fkey FOREIGN KEY (id_budget) REFERENCES tb_budget (id_budget);

alter table tb_budget_product_reserved
    ADD CONSTRAINT tb_budget_product_reserved_product_fkey FOREIGN KEY (id_product) REFERENCES tb_product (id_product);

