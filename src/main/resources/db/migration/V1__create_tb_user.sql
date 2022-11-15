create table if not exists tb_user (
    id_user int8 not null,
    user_name varchar(100) not null,
    user_password text not null,
    user_email varchar(40) not null,
    user_birthday date not null,
    user_phone varchar(50) not null,
    user_status varchar(20) not null
);

alter table tb_user ADD CONSTRAINT tb_user_pkey PRIMARY KEY (id_user);
