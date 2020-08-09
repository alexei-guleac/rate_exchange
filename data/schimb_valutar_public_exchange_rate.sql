create table exchange_rate
(
    id         bigint not null
        constraint exchange_rate_pkey
            primary key,
    currency   integer,
    factor     integer,
    rate       double precision,
    updated_at timestamp
);

alter table exchange_rate
    owner to postgres;

INSERT INTO public.exchange_rate (id, currency, factor, rate, updated_at) VALUES (1, 0, 1, 16.62, '2020-08-09 17:44:50.014000');
INSERT INTO public.exchange_rate (id, currency, factor, rate, updated_at) VALUES (2, 1, 1, 19.68, '2020-08-09 17:44:50.014000');
INSERT INTO public.exchange_rate (id, currency, factor, rate, updated_at) VALUES (3, 2, 1, 10.07, '2020-08-09 17:44:50.014000');