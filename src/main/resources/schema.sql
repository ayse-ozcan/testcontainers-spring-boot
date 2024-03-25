CREATE TABLE IF NOT EXISTS books
(
    id serial primary key,
    name varchar(255) not null,
    author varchar(255) not null
);