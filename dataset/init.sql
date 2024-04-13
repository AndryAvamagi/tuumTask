CREATE TABLE transactions (
    accountId varchar,
    transactionId varchar,
    amount NUMERIC,
    actionTime timestamp
);

COPY transactions
FROM '/docker-entrypoint-initdb.d/data.csv'
DELIMITER ','
CSV HEADER;
