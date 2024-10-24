--liquibase formatted sql
--changeset urlapov:example

CREATE TABLE ping_pong (
   id       UUID            PRIMARY KEY,
   value    VARCHAR(255)    NOT NULL
);