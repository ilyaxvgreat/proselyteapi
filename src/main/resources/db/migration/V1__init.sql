DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS stocks;
CREATE TABLE accounts
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(36) NOT NULL,
    password VARCHAR(36) NOT NULL,
    api_key  VARCHAR(36) NOT NULL
);
CREATE TABLE companies
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    code VARCHAR(36) NOT NULL
);
CREATE TABLE stocks
(
    id           SERIAL PRIMARY KEY,
    price        INT         NOT NULL,
    created_date TIMESTAMP   NOT NULL DEFAULT current_timestamp,
    code         VARCHAR(36) NOT NULL
);

