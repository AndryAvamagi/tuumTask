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


Insert into transactions (transactionId, accountId, amount, currency, direction, description) values ('4a1b6caa-dafc-47e6-b8cf-9cc5b0db8838', 'acc1', 0.0, 'EUR', 'IN', 'CREATED ACCOUNT');
Insert into transactions (transactionId, accountId, amount, currency, direction, description) values ('8ab446b8-0279-4eb9-9514-761b9ec79cb0', 'acc1', 0.0, 'USD', 'IN', 'CREATED ACCOUNT');
Insert into transactions (transactionId, accountId, amount, currency, direction, description) values ('7990dd15-c6fa-4958-b9dc-b948d01e4126', 'acc1', 0.0, 'GBP', 'IN', 'CREATED ACCOUNT');
Insert into transactions (transactionId, accountId, amount, currency, direction, description) values ('e78db400-febe-4a1f-9f7a-1f0af744ba86', 'acc1', 50.0, 'EUR', 'IN', 'abc');

insert into balances (accountid, currency, totalamount) values ('acc1', 'EUR', 50.0);
insert into balances (accountid, currency, totalamount) values ('acc1', 'USD', 0.0);
insert into balances (accountid, currency, totalamount) values ('acc1', 'GBP', 0.0);

insert into Accounts (accountid, customerid, country) values  ('acc1', 'AndryAvamagi', 'EST')



-- {
--         "transactionId": "4a1b6caa-dafc-47e6-b8cf-9cc5b0db8838",
--         "accountId": "acc1",
--         "amount": 0.0,
--         "currency": "EUR",
--         "direction": "IN",
--         "description": "CREATED ACCOUNT"
--     },
--     {
--         "transactionId": "8ab446b8-0279-4eb9-9514-761b9ec79cb0",
--         "accountId": "acc1",
--         "amount": 0.0,
--         "currency": "USD",
--         "direction": "IN",
--         "description": "CREATED ACCOUNT"
--     },
--     {
--         "transactionId": "7990dd15-c6fa-4958-b9dc-b948d01e4126",
--         "accountId": "acc1",
--         "amount": 0.0,
--         "currency": "GBP",
--         "direction": "IN",
--         "description": "CREATED ACCOUNT"
--     },
--     {
--         "transactionId": "e78db400-febe-4a1f-9f7a-1f0af744ba86",
--         "accountId": "acc1",
--         "amount": 50.0,
--         "currency": "EUR",
--         "direction": "IN",
--         "description": "abc"
--     }











-- COPY Transactions
-- FROM '/docker-entrypoint-initdb.d/data.csv'
-- DELIMITER ','
-- CSV HEADER;

-- COPY Accounts
-- FROM '/docker-entrypoint-initdb.d/AccountsData.csv'
-- DELIMITER ','
-- CSV HEADER;
