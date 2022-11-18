create table if not exists tb_category_file
(
    id_file   int auto_increment primary key not null,
    file_name varchar(255) not null,
    file_type varchar(100) not null,
    file_byte blob         not null
);

create table if not exists tb_category
(
    id_category          int auto_increment primary key not null,
    id_file              int         not null,
    category_name        varchar(100) not null,
    category_description text         not null
);

alter table tb_category
    ADD CONSTRAINT tb_category_category_file_fkey FOREIGN KEY (id_file) REFERENCES tb_category_file (id_file);

