CREATE SEQUENCE users_id_seq;

CREATE TABLE users (
    id bigint NOT NULL DEFAULT NEXTVAL('users_id_seq'),
    email varchar(255) NOT NULL,
    first_name varchar(255),
    last_name varchar(255) NOT NULL,
    PRIMARY KEY (id)
)