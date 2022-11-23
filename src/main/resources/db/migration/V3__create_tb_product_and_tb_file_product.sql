create table if not exists tb_product_file
(
    id_file   int auto_increment primary key not null,
    file_name varchar(255)                   not null,
    file_type varchar(100)                   not null,
    file_byte longblob                       not null
);

create table if not exists tb_product
(
    id_product               int auto_increment primary key not null,
    id_category              int                            not null,
    id_file                  int                            not null,
    product_code             varchar(100)                   not null,
    product_name             varchar(120)                   not null,
    product_description      text                           not null,
    product_value            decimal(10, 2)                 not null,
    product_discount_value   decimal(10, 2)                 not null,
    product_percentage_value decimal(5, 4)                  not null,
    product_minimun_order    int                            not null default false,
    product_exist_percentage bool                           not null,
    product_tag              varchar(100)                   not null,
    product_with_stock       bool                           not null default false
);

alter table tb_product
    ADD CONSTRAINT tb_product_category_fkey FOREIGN KEY (id_category) REFERENCES tb_category (id_category);

alter table tb_product
    ADD CONSTRAINT tb_product_product_file_fkey FOREIGN KEY (id_file) REFERENCES tb_product_file (id_file);

