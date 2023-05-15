create table if not exists tb_parameterization
(
    id_param                int auto_increment primary key not null,
    param_freight_cost      decimal(10, 2)                 not null,
    param_pix_api_key       text,
    param_pix_client_key    text,
    param_pix_account_key   text,
    param_paypal_client_key text,
    param_paypal_secret_key text,
    param_environment       varchar(30) not null,
    param_city_limit        text
);

insert into tb_parameterization(id_param,
                                param_freight_cost,
                                param_pix_api_key,
                                param_pix_client_key,
                                param_pix_account_key,
                                param_paypal_client_key,
                                param_paypal_secret_key,
                                param_environment,
                                param_city_limit)
values (1,
        4.00,
        'Q2xpZW50X0lkXzIzNGI1MjgyLTdkNGQtNGY5Zi1iODAzLWE3NTdiNjUyMmZhZjpDbGllbnRfU2VjcmV0X2JTdkVKL1ozQVBka1dyZndxeEVoalZ5dkFzWUlZVDIwUXpnTzhjTHdBQUk9',
        'Client_Id_234b5282-7d4d-4f9f-b803-a757b6522faf',
        '2779240c-c209-48bf-b3b0-5936aaf5dcaf',
        'AciD2XN8sSajUz8UsnqgXIHYhyr0xzYDCJmSh9tW-UEj2W3sqRESFYgUf1JPYt6rHZnjy87Bg7CGrOQS',
        'EOdC3pIePdszNUK4oNicabiQ1Z6Bw_mDHivce6OPfjEcRnpXFIrvv4JobH8NshDTrOST6Z76aZ3MkRIN',
        'HOMOLOGATION',
        'Bom Despacho');

