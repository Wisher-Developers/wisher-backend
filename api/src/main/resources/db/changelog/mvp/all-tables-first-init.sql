--liquibase formatted sql
--changeset garnetaki:tables-init

CREATE TABLE users (
   id               UUID            PRIMARY KEY UNIQUE,
   email            VARCHAR(255)    NOT NULL UNIQUE,
   login            VARCHAR(255)    NOT NULL UNIQUE,
   password         VARCHAR(255)    NOT NULL UNIQUE,
   avatar           VARCHAR(255)
);

CREATE TABLE wishlists (
   id               UUID            PRIMARY KEY UNIQUE,
   name             VARCHAR(255)    NOT NULL,
   description      VARCHAR(10000),
   access_link      VARCHAR(255)    UNIQUE,
   private_mode     VARCHAR(255),
   position         INTEGER         NOT NULL,
   owner_id         UUID            FOREIGN KEY NOT NULL REFERENCES wisher.users(id)
);

CREATE TABLE items (
   id               UUID            PRIMARY KEY UNIQUE,
   name             VARCHAR(255)    NOT NULL,
   link             VARCHAR(255),
   priority         INTEGER,
   price            INTEGER,
   picture          VARCHAR(255),
   description      VARCHAR(10000),
   wishlist_id      UUID            FOREIGN KEY NOT NULL REFERENCES wisher.wishlists(id)
   position         INTEGER         NOT NULL,
   idempotency_id   INTEGER         NOT NULL
);

CREATE TABLE wishlist_access (
   wishlist_id      UUID           FOREIGN KEY NOT NULL REFERENCES wisher.wishlists(id),
   user_id          UUID           FOREIGN KEY NOT NULL REFERENCES wisher.users(id),
   PRIMARY KEY(wishlist_id, user_id)
);

CREATE TABLE friends (
   user_id_first    UUID           FOREIGN KEY NOT NULL REFERENCES wisher.users(id),
   user_id_second   UUID           FOREIGN KEY NOT NULL REFERENCES wisher.users(id),
   PRIMARY KEY(user_id_first, user_id_second)
);

CREATE TABLE friend_requests (
   sender_id        UUID           FOREIGN KEY NOT NULL REFERENCES wisher.users(id),
   receiver_id      UUID           FOREIGN KEY NOT NULL REFERENCES wisher.users(id),
   PRIMARY KEY(sender_id, receiver_id)
);

CREATE TABLE item_user (
   item_id          UUID           FOREIGN KEY NOT NULL REFERENCES wisher.items(id),
   user_id          UUID           FOREIGN KEY NOT NULL REFERENCES wisher.users(id),
   index_number     INTEGER        NOT NULL,
   PRIMARY KEY(item_id, user_id)
);
