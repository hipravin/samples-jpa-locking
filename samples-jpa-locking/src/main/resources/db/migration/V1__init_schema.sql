CREATE TABLE client
(
    client_id BIGSERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    email      TEXT NOT NULL UNIQUE,
    CONSTRAINT name_not_empty CHECK (first_name !~ '\s' AND last_name !~ '\s'),
	CONSTRAINT first_name_length CHECK (length(first_name) <= 512),
	CONSTRAINT last_name_length CHECK (length(last_name) <= 512)
);

ALTER SEQUENCE client_client_id_seq INCREMENT BY 100 RESTART 100;

CREATE TABLE account
(
    account_id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL REFERENCES client(client_id),
    available_amount MONEY NOT NULL
);

ALTER SEQUENCE account_account_id_seq INCREMENT BY 100 RESTART 100;
CREATE INDEX account_client_id_idx ON account(client_id);