-- CREATE TABLE transactions (
--     accountId varchar,
--     transactionId varchar,
--     amount NUMERIC,
--     actionTime timestamp
-- );

-- COPY transactions
-- FROM '/docker-entrypoint-initdb.d/data.csv'
-- DELIMITER ','
-- CSV HEADER;

CREATE TABLE Currencies (
    AccountId VARCHAR,
    Currency VARCHAR
);

CREATE TABLE Accounts (
    AccountId VARCHAR,
    CustomerId VARCHAR
);

CREATE TABLE Customers (
    CustomerId VARCHAR,
    Country VARCHAR,
    Name VARCHAR,
    Surname VARCHAR
);

CREATE TABLE Transactions (
    TransactionId VARCHAR,
    AccountId VARCHAR,
    Amount NUMERIC,
    Currency VARCHAR,
    AmountInEur NUMERIC,
    Direction VARCHAR,
    Description VARCHAR
);

COPY Transactions
FROM '/docker-entrypoint-initdb.d/data.csv'
DELIMITER ','
CSV HEADER;

COPY Accounts
FROM '/docker-entrypoint-initdb.d/AccountsData.csv'
DELIMITER ','
CSV HEADER;

COPY Customers
FROM '/docker-entrypoint-initdb.d/CustomersData.csv'
DELIMITER ','
CSV HEADER;

COPY Currencies
FROM '/docker-entrypoint-initdb.d/CurrencyData.csv'
DELIMITER ','
CSV HEADER;
