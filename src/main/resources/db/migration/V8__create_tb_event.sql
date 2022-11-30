create table if not exists tb_event
(
    id_event         int auto_increment primary key not null,
    id_device        int                            not null,
    event_title      text                           not null,
    event_message    text                           not null,
    event_image      text,
    event_status     varchar(100)                   not null,
    event_send       bool                           not null default false,
    event_visualized bool                           not null default false,
    event_date_send  datetime                       not null
);

alter table tb_event
    ADD CONSTRAINT tb_event_device_fkey FOREIGN KEY (id_device) REFERENCES tb_device (id_device);

