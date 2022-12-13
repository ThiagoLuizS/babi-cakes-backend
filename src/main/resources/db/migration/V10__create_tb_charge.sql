create table if not exists tb_charge
(
    id_charge             int auto_increment primary key not null,
    id_budget             int                            not null,
    charge_expiration     int                            not null,
    charge_expires_date   datetime                       not null,
    charge_created        datetime                       not null,
    charge_tax_ID         text                           not null,
    charge_value          decimal(10, 2)                 not null,
    charge_correlation_ID text                           not null,
    charge_transaction_ID text                           not null,
    charge_status         text                           not null,
    charge_br_code        text                           not null
);

alter table tb_charge
    ADD CONSTRAINT tb_charge_budget_fkey FOREIGN KEY (id_budget) REFERENCES tb_budget (id_budget);

