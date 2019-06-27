CREATE SEQUENCE users_id_seq;

CREATE TABLE users (
    id bigint UNIQUE NOT NULL DEFAULT NEXTVAL('users_id_seq'),
    username varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) UNIQUE NOT NULL,
    first_name varchar(255),
    last_name varchar(255),
    avatar_url VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE SEQUENCE events_id_seq;

CREATE TABLE events (
    id bigint UNIQUE NOT NULL DEFAULT NEXTVAL('events_id_seq'),
    event_name VARCHAR(255) NOT NULL,
    dest VARCHAR(255),
    dest_id VARCHAR(255),
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    PRIMARY KEY (id)
)