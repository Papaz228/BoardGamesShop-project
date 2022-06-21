DROP DATABASE postgres;

CREATE DATABASE IF NOT EXISTS postgres;


create schema IF NOT EXISTS "BoardGames";


create sequence status_id_seq;

create sequence order_detail_id_seq;

create sequence product_id_seq;

create sequence country_id_seq;

create sequence product_category_id_seq;

create table if not exists "User"
(
    id           bigint generated always as identity (maxvalue 2147483647),
    first_name   varchar not null,
    last_name    varchar not null,
    birthday     varchar not null,
    phone_number varchar not null,
    email        varchar not null,
    password     varchar not null,
    is_admin     boolean not null,
    is_banned    boolean not null,
    constraint user_pk
        primary key (id)
);

create table if not exists "Local"
(
    id         bigint generated always as identity,
    short_name varchar not null,
    name       varchar not null,
    constraint local_pk
        primary key (id)
);

create table if not exists "Status"
(
    id       bigint generated always as identity,
    local_id bigint  not null,
    name     varchar not null,
    constraint status_pk
        primary key (id),
    constraint status_local_id_fk
        foreign key (local_id) references "Local"
);

alter sequence status_id_seq owned by "Status".id;

create table if not exists "Order"
(
    id          bigint generated always as identity,
    total_cost  integer not null,
    date_start  date    not null,
    date_finish date    not null,
    user_id     bigint  not null,
    status_id   bigint  not null,
    constraint order_pk
        primary key (id),
    constraint order_un
        unique (id),
    constraint "order_fkUser"
        foreign key (user_id) references "User"
            on update set default on delete set default,
    constraint order_fk
        foreign key (status_id) references "Status"
            on update set default on delete set default
);

create table if not exists "Country"
(
    id       bigint generated always as identity,
    local_id bigint  not null,
    name     varchar not null,
    constraint country_pk
        primary key (id),
    constraint country_local_id_fk
        foreign key (local_id) references "Local"
);

alter sequence country_id_seq owned by "Country".id;

create table if not exists "Product_category"
(
    id            bigint generated always as identity,
    local_id      bigint  not null,
    category_name varchar not null,
    constraint product_category_pk
        primary key (id),
    constraint product_category_local_id_fk
        foreign key (local_id) references "Local"
);

alter sequence product_category_id_seq owned by "Product_category".id;

create table if not exists "Product"
(
    id                  bigint generated always as identity,
    name                varchar not null,
    description         varchar not null,
    cost                integer not null,
    count               integer not null,
    country_id          bigint  not null,
    product_category_id bigint  not null,
    isactive            boolean not null,
    constraint product_pk
        primary key (id),
    constraint product_country_id_fk
        foreign key (country_id) references "Country",
    constraint product_product_category_id_fk
        foreign key (product_category_id) references "Product_category"
            on update set default on delete set default
);

alter sequence product_id_seq owned by "Product".id;

create table if not exists "Order_detail"
(
    id         bigint generated always as identity,
    order_id   bigint  not null,
    product_id bigint  not null,
    count      integer not null,
    cost       integer not null,
    constraint order_detail_pk
        primary key (id),
    constraint order_detail_order_id_fk
        foreign key (order_id) references "Order",
    constraint order_detail_fk
        foreign key (product_id) references "Product"
);

alter sequence order_detail_id_seq owned by "Order_detail".id;

create table if not exists "Basket"
(
    user_id    bigint  not null,
    product_id bigint  not null,
    count      integer not null,
    id         bigint generated always as identity,
    constraint basket_pk
        primary key (id),
    constraint basket_user_id_fk
        foreign key (user_id) references "User",
    constraint basket_product_id_fk
        foreign key (product_id) references "Product"
);
