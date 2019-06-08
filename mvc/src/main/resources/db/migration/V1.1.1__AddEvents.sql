CREATE SEQUENCE events_id_seq;

CREATE TABLE events (
    id bigint NOT NULL DEFAULT NEXTVAL('events_id_seq'),
    event_name VARCHAR (255) NOT NULL,
    dest VARCHAR (255),
    dest_id VARCHAR (255),
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    PRIMARY KEY (id)
)
