CREATE SEQUENCE photos_id_seq;

CREATE TABLE photos (
    id bigint UNIQUE NOT NULL DEFAULT NEXTVAL('photos_id_seq'),
    name varchar(255) NOT NULL,
    s3_key varchar(255) UNIQUE NOT NULL,
    create_date_time TIMESTAMP,
    user_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_photos_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);