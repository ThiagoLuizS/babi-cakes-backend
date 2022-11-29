create table if not exists tb_device
(
    id_device          int auto_increment primary key not null,
    id_user            int                            not null,
    device_brand       text                           not null,
    device_model       text                           not null,
    device_token       text                           not null,
    device_date_create datetime                       not null
);

alter table tb_device
    ADD CONSTRAINT tb_device_user_fkey FOREIGN KEY (id_user) REFERENCES tb_user (id_user);

