--liquibase formatted sql
--changeset garnetaki:change-idempotency-id-to-uuid

ALTER TABLE item DROP COLUMN idempotency_id;

ALTER TABLE item ADD COLUMN idempotency_id UUID;
