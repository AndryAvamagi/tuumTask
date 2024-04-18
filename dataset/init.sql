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

CREATE TABLE Accounts (
    AccountId VARCHAR,
    CustomerId VARCHAR,
    Country VARCHAR
);


CREATE TABLE Transactions (
    TransactionId VARCHAR,
    AccountId VARCHAR,
    Amount NUMERIC,
    Currency VARCHAR,
    Direction VARCHAR,
    Description VARCHAR
);

CREATE TABLE Balances (
    AccountId VARCHAR,
    Currency VARCHAR,
    TotalAmount numeric
);

-- COPY Transactions
-- FROM '/docker-entrypoint-initdb.d/data.csv'
-- DELIMITER ','
-- CSV HEADER;

-- COPY Accounts
-- FROM '/docker-entrypoint-initdb.d/AccountsData.csv'
-- DELIMITER ','
-- CSV HEADER;
