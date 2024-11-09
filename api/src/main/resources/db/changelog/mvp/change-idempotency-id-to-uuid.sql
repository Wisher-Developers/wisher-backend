--liquibase formatted sql
--changeset garnetaki:tables-init

ALTER TABLE item ALTER COLUMN idempotency_id TYPE UUID;
