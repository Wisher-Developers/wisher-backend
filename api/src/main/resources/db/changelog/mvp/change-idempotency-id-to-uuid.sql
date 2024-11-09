--liquibase formatted sql
--changeset garnetaki:tables-init

ALTER TABLE people ALTER COLUMN idempotency_id TYPE UUID;
