CREATE SCHEMA boardgames;

CREATE SEQUENCE boardgames.basket_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.country_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.local_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.order_detail_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.order_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.product_category_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.product_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.status_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE boardgames.user_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;

CREATE TABLE local (
                         id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                         short_name varchar(45) NOT NULL,
                         name varchar(45) NOT NULL,
                         CONSTRAINT local_pk PRIMARY KEY (id)
);

CREATE TABLE users (
                       id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                       first_name varchar(45) NOT NULL,
                       last_name varchar(45) NOT NULL,
                       birthday varchar(45) NOT NULL,
                       phone_number varchar(45) NOT NULL,
                       email varchar(45) NOT NULL,
                       password varchar(45) NOT NULL,
                       is_admin bool NOT NULL,
                       is_banned bool NOT NULL,
                       CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE TABLE country (
                         id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                         local_id int8 NOT NULL,
                         name varchar(45) NOT NULL,
                         CONSTRAINT country_pk PRIMARY KEY (id),
                         CONSTRAINT country_local_id_fk FOREIGN KEY (local_id) REFERENCES local(id)
);

CREATE TABLE product_category (
                                  id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                  local_id int8 NOT NULL,
                                  category_name varchar(45) NOT NULL,
                                  CONSTRAINT product_category_pk PRIMARY KEY (id),
                                  CONSTRAINT product_category_local_id_fk FOREIGN KEY (local_id) REFERENCES local(id)
);

CREATE TABLE status (
                        id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                        local_id int8 NOT NULL,
                        name varchar(45) NOT NULL,
                        CONSTRAINT status_pk PRIMARY KEY (id),
                        CONSTRAINT status_local_id_fk FOREIGN KEY (local_id) REFERENCES local(id)
);

CREATE TABLE orders (
                        id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                        total_cost float8 NOT NULL,
                        date_start date NOT NULL,
                        user_id int8 NOT NULL,
                        status_id int8 NOT NULL,
                        CONSTRAINT order_pk PRIMARY KEY (id),
                        CONSTRAINT order_fk FOREIGN KEY (status_id) REFERENCES status(id) ON DELETE SET DEFAULT ON UPDATE SET DEFAULT,
                        CONSTRAINT order_fkUser FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET DEFAULT ON UPDATE SET DEFAULT
);

CREATE TABLE product (
                         id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                         name varchar(45) NOT NULL,
                         description varchar(80) NOT NULL,
                         cost int4 NOT NULL,
                         count int4 NOT NULL,
                         country_id int8 NOT NULL,
                         product_category_id int8 NOT NULL,
                         is_active bool NOT NULL,
                         photo_url varchar(100) NULL,
                         CONSTRAINT product_pk PRIMARY KEY (id),
                         CONSTRAINT product_country_id_fk FOREIGN KEY (country_id) REFERENCES country(id),
                         CONSTRAINT product_product_category_id_fk FOREIGN KEY (product_category_id) REFERENCES product_category(id) ON DELETE SET DEFAULT ON UPDATE SET DEFAULT
);

CREATE TABLE basket (
                        user_id int8 NOT NULL,
                        product_id int8 NOT NULL,
                        count int4 NOT NULL,
                        id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                        CONSTRAINT basket_pk PRIMARY KEY (id),
                        CONSTRAINT basket_product_id_fk FOREIGN KEY (product_id) REFERENCES product(id),
                        CONSTRAINT basket_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_detail (
                              id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                              order_id int8 NOT NULL,
                              product_id int8 NOT NULL,
                              count int4 NOT NULL,
                              cost int4 NOT NULL,
                              CONSTRAINT order_detail_pk PRIMARY KEY (id),
                              CONSTRAINT order_detail_fk FOREIGN KEY (product_id) REFERENCES product(id),
                              CONSTRAINT order_detail_order_id_fk FOREIGN KEY (order_id) REFERENCES orders(id)
);