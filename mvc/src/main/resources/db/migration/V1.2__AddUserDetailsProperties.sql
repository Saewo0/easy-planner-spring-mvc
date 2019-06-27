ALTER TABLE users
ADD COLUMN account_non_expired BOOLEAN,
ADD COLUMN account_non_locked BOOLEAN,
ADD COLUMN credentials_non_expired BOOLEAN,
ADD COLUMN enabled BOOLEAN;