--liquibase formatted sql
--changeset garnetaki:add-items

ALTER TABLE item ALTER COLUMN idempotency_id TYPE UUID;
