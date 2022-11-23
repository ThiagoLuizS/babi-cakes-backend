create table if not exists tb_user (
    id_user int auto_increment primary key not null,
    user_name varchar(100) not null,
    user_password text not null,
    user_email varchar(40) not null,
    user_birthday date not null,
    user_phone varchar(50) not null,
    user_status varchar(20) not null
);
