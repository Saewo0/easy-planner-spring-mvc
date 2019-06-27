CREATE SEQUENCE authorities_id_seq;

CREATE TABLE authorities (
    id bigint UNIQUE NOT NULL DEFAULT NEXTVAL('authorities_id_seq'),
    user_id bigint NOT NULL,
    role_type varchar(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_authorities_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);