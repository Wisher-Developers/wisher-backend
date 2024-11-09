--liquibase formatted sql
--changeset garnetaki:tables-init

CREATE TABLE users (
    id               UUID            PRIMARY KEY UNIQUE,
    email            VARCHAR(255)    NOT NULL UNIQUE,
    login            VARCHAR(255)    NOT NULL UNIQUE,
    password         VARCHAR(255)    NOT NULL,
    avatar           VARCHAR(255)
);

CREATE TABLE wishlist (
    id               UUID            PRIMARY KEY UNIQUE,
    name             VARCHAR(255)    NOT NULL,
    description      VARCHAR(10000),
    access_link      VARCHAR(255)    UNIQUE,
    private_mode     VARCHAR(255),
    position         INTEGER         NOT NULL,
    owner_id         UUID,

    FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE item (
    id               UUID            PRIMARY KEY UNIQUE,
    name             VARCHAR(255)    NOT NULL,
    link             VARCHAR(255),
    priority         INTEGER,
    price            INTEGER,
    picture          VARCHAR(255),
    description      VARCHAR(10000),
    wishlist_id      UUID            NOT NULL,
    position         INTEGER         NOT NULL,
    idempotency_id   INTEGER         NOT NULL,

    FOREIGN KEY (wishlist_id) REFERENCES wishlist(id)
);

CREATE TABLE wishlist_access (
    wishlist_id      UUID           NOT NULL,
    user_id          UUID           NOT NULL,

    PRIMARY KEY(wishlist_id, user_id),
    FOREIGN KEY (wishlist_id) REFERENCES wishlist(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE friend (
    user_id_first    UUID           NOT NULL,
    user_id_second   UUID           NOT NULL,

    PRIMARY KEY(user_id_first, user_id_second),
    FOREIGN KEY (user_id_first) REFERENCES users(id),
    FOREIGN KEY (user_id_second) REFERENCES users(id)
);

CREATE TABLE friend_request (
    sender_id        UUID           NOT NULL,
    receiver_id      UUID           NOT NULL,

    PRIMARY KEY(sender_id, receiver_id),
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);

CREATE TABLE item_user (
   item_id          UUID           NOT NULL,
   user_id          UUID           NOT NULL,
   index_number     INTEGER        NOT NULL,

   PRIMARY KEY(item_id, user_id),
   FOREIGN KEY (item_id) REFERENCES item(id),
   FOREIGN KEY (user_id) REFERENCES users(id)
);
