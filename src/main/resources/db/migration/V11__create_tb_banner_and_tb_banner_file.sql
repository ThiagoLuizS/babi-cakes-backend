create table if not exists tb_banner_file
(
    id_file   int auto_increment primary key not null,
    file_name varchar(255)                   not null,
    file_type varchar(100)                   not null,
    file_byte longblob                       not null
);

create table if not exists tb_banner
(
    id_banner    int auto_increment primary key not null,
    id_product   int,
    id_category  int,
    id_file      int                            not null,
    banner_title varchar(100)                   not null
);

alter table tb_banner
    ADD CONSTRAINT tb_banner_product_fkey FOREIGN KEY (id_product) REFERENCES tb_product (id_product);

alter table tb_banner
    ADD CONSTRAINT tb_banner_category_fkey FOREIGN KEY (id_category) REFERENCES tb_category (id_category);

alter table tb_banner
    ADD CONSTRAINT tb_banner_category_file_fkey FOREIGN KEY (id_file) REFERENCES tb_banner_file (id_file);