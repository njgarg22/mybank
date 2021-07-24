create table if not exists transactions
(
    id      uuid  default random_uuid() primary key,
    amount  DECIMAL(15, 4) NOT NULL,
    `timestamp` timestamp NOT NULL,
    reference varchar(255) NOT NULL,
    bank_slogan varchar(255),
    receiving_user_id varchar(255) NOT NULL
);