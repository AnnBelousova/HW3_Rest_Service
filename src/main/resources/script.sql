-- create database if not exists restdb;
drop table if exists users;
drop table if exists claims;
drop table if exists product;
drop table if exists claim_product;
create table if not exists users
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE
);

create table if not exists claims
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(255),
    user_id     INTEGER,
    FOREIGN KEY (user_id)
        REFERENCES users (id)
);

create table if not exists product
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL UNIQUE,
    weight INTEGER
);

create table if not exists claim_product
(
    product_id INTEGER NOT NULL,
    claim_id   INTEGER NOT NULL,
    PRIMARY KEY (product_id, claim_id),
    FOREIGN KEY (product_id)
        REFERENCES product (id),
    FOREIGN KEY (claim_id)
        REFERENCES claims (id) ON DELETE CASCADE
);
    INSERT INTO public.users(name, email)
    VALUES ('user1','email1'),
           ('user2','email2');

INSERT INTO public.product(name, weight)
VALUES ('product1',100),
       ('product2',200),
       ('product3',300);

INSERT INTO public.claims(description, user_id)
VALUES ('descr1',1),
       ('descr2',1);

INSERT INTO public.claim_product(product_id, claim_id)
VALUES (1,1),
       (1,2);