CREATE TABLE accounts (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number     VARCHAR(20)    NOT NULL UNIQUE,
    owner_name VARCHAR(100)   NOT NULL,
    currency   VARCHAR(3)     NOT NULL,
    balance    NUMERIC(19, 2) NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ    NOT NULL DEFAULT now(),

    CONSTRAINT accounts_balance_non_negative CHECK (balance >= 0)
)