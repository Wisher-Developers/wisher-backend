--liquibase formatted sql
--changeset urlapov:add_last_login_for_user

ALTER TABLE users
    ADD COLUMN last_login TIMESTAMP,
    ADD COLUMN last_request_recommendation_id UUID NULL;
