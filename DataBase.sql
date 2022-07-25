-- DROP SCHEMA "BoardGames";

CREATE SCHEMA "BoardGames";

-- DROP SEQUENCE "BoardGames".basket_id_seq;

CREATE SEQUENCE BoardGame.basket_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".country_id_seq;

CREATE SEQUENCE BoardGame.country_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".local_id_seq;

CREATE SEQUENCE BoardGame.local_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".order_detail_id_seq;

CREATE SEQUENCE BoardGame.order_detail_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".order_id_seq;

CREATE SEQUENCE BoardGame.order_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".product_category_id_seq;

CREATE SEQUENCE BoardGame.product_category_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".product_id_seq;

CREATE SEQUENCE BoardGame.product_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".status_id_seq;

CREATE SEQUENCE BoardGame.status_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE "BoardGames".user_id_seq;

CREATE SEQUENCE BoardGame.user_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
    CACHE 1
    NO CYCLE;-- "BoardGames"."Local" definition

-- Drop table

-- DROP TABLE "Local";

CREATE TABLE "Local" (
                         id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                         short_name varchar NOT NULL,
                         "name" varchar NOT NULL,
                         CONSTRAINT local_pk PRIMARY KEY (id)
);


-- "BoardGames"."User" definition

-- Drop table

-- DROP TABLE "User";

CREATE TABLE "User" (
                        id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                        first_name varchar NOT NULL,
                        last_name varchar NOT NULL,
                        birthday varchar NOT NULL,
                        phone_number varchar NOT NULL,
                        email varchar NOT NULL,
                        "password" varchar NOT NULL,
                        is_admin bool NOT NULL,
                        is_banned bool NOT NULL,
                        CONSTRAINT user_pk PRIMARY KEY (id)
);


-- "BoardGames"."Country" definition

-- Drop table

-- DROP TABLE "Country";

CREATE TABLE "Country" (
                           id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                           local_id int8 NOT NULL,
                           "name" varchar NOT NULL,
                           CONSTRAINT country_pk PRIMARY KEY (id),
                           CONSTRAINT country_local_id_fk FOREIGN KEY (local_id) REFERENCES "Local"(id)
);


-- "BoardGames"."Product_category" definition

-- Drop table

-- DROP TABLE "Product_category";

CREATE TABLE "Product_category" (
                                    id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                    local_id int8 NOT NULL,
                                    category_name varchar NOT NULL,
                                    CONSTRAINT product_category_pk PRIMARY KEY (id),
                                    CONSTRAINT product_category_local_id_fk FOREIGN KEY (local_id) REFERENCES "Local"(id)
);


-- "BoardGames"."Status" definition

-- Drop table

-- DROP TABLE "Status";

CREATE TABLE "Status" (
                          id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                          local_id int8 NOT NULL,
                          "name" varchar NOT NULL,
                          CONSTRAINT status_pk PRIMARY KEY (id),
                          CONSTRAINT status_local_id_fk FOREIGN KEY (local_id) REFERENCES "Local"(id)
);


-- "BoardGames"."Order" definition

-- Drop table

-- DROP TABLE "Order";

CREATE TABLE "Order" (
                         id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                         total_cost int4 NOT NULL,
                         date_start date NOT NULL,
                         user_id int8 NOT NULL,
                         status_id int8 NOT NULL,
                         CONSTRAINT order_pk PRIMARY KEY (id),
                         CONSTRAINT order_fk FOREIGN KEY (status_id) REFERENCES "Status"(id) ON DELETE SET DEFAULT ON UPDATE SET DEFAULT,
                         CONSTRAINT "order_fkUser" FOREIGN KEY (user_id) REFERENCES "User"(id) ON DELETE SET DEFAULT ON UPDATE SET DEFAULT
);


-- "BoardGames"."Product" definition

-- Drop table

-- DROP TABLE "Product";

CREATE TABLE "Product" (
                           id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                           "name" varchar NOT NULL,
                           description varchar NOT NULL,
                           "cost" int4 NOT NULL,
                           count int4 NOT NULL,
                           country_id int8 NOT NULL,
                           product_category_id int8 NOT NULL,
                           is_active bool NOT NULL,
                           photo_url varchar NULL,
                           CONSTRAINT product_pk PRIMARY KEY (id),
                           CONSTRAINT product_country_id_fk FOREIGN KEY (country_id) REFERENCES "Country"(id),
                           CONSTRAINT product_product_category_id_fk FOREIGN KEY (product_category_id) REFERENCES "Product_category"(id) ON DELETE SET DEFAULT ON UPDATE SET DEFAULT
);


-- "BoardGames"."Basket" definition

-- Drop table

-- DROP TABLE "Basket";

CREATE TABLE "Basket" (
                          user_id int8 NOT NULL,
                          product_id int8 NOT NULL,
                          count int4 NOT NULL,
                          id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                          CONSTRAINT basket_pk PRIMARY KEY (id),
                          CONSTRAINT basket_product_id_fk FOREIGN KEY (product_id) REFERENCES "Product"(id),
                          CONSTRAINT basket_user_id_fk FOREIGN KEY (user_id) REFERENCES "User"(id)
);


-- "BoardGames"."Order_detail" definition

-- Drop table

-- DROP TABLE "Order_detail";

CREATE TABLE "Order_detail" (
                                id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                order_id int8 NOT NULL,
                                product_id int8 NOT NULL,
                                count int4 NOT NULL,
                                "cost" int4 NOT NULL,
                                CONSTRAINT order_detail_pk PRIMARY KEY (id),
                                CONSTRAINT order_detail_fk FOREIGN KEY (product_id) REFERENCES "Product"(id),
                                CONSTRAINT order_detail_order_id_fk FOREIGN KEY (order_id) REFERENCES "Order"(id)
);
