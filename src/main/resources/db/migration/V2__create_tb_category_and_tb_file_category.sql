create table if not exists tb_category_file
(
    id_file   int8         not null,
    file_name varchar(255) not null,
    file_type varchar(100) not null,
    file_byte blob         not null
);


alter table tb_category_file
    ADD CONSTRAINT tb_category_file_pkey PRIMARY KEY (id_file);

create table if not exists tb_category
(
    id_category          int8         not null,
    id_file              int8         not null,
    category_name        varchar(100) not null,
    category_description text         not null
);

alter table tb_category
    ADD CONSTRAINT tb_category_pkey PRIMARY KEY (id_category);

alter table tb_category
    ADD CONSTRAINT tb_category_category_file_fkey FOREIGN KEY (id_file) REFERENCES tb_category_file (id_file);
