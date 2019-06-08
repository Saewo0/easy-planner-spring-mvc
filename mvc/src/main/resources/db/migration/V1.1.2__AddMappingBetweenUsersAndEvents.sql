ALTER TABLE events ADD COLUMN host_id bigint NOT NULL;

ALTER TABLE events ADD CONSTRAINT fk_events_user
                          FOREIGN KEY (host_id)
                          REFERENCES users(id)
                          ON DELETE NO ACTION
                          ON UPDATE NO ACTION;
