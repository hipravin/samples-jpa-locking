CREATE SEQUENCE client_client_id_seq START WITH 200 INCREMENT BY 100;
CREATE SEQUENCE account_account_id_seq START WITH 200 INCREMENT BY 100;

CREATE TABLE client
(
    client_id BIGSERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    email      TEXT NOT NULL UNIQUE,
    CONSTRAINT first_name_length CHECK (length(first_name) <= 512),
    CONSTRAINT last_name_length CHECK (length(last_name) <= 512)
);


CREATE TABLE account
(
    account_id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL REFERENCES client(client_id),
    available_amount NUMERIC(19,2) NOT NULL
);

CREATE INDEX account_client_id_idx ON account(client_id);