CREATE TABLE IF NOT EXISTS todo
(
    id
    serial
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
) NOT NULL,
    description VARCHAR
(
    50
) NOT NULL,
    done VARCHAR
(
    255
) NOT NULL,
    duration VARCHAR
(
    255
) NOT NULL
    );

CREATE TABLE IF NOT EXISTS _user
(
    id
    serial
    PRIMARY
    KEY,
    email
    VARCHAR
(
    50
) NOT NULL,
    first_name VARCHAR
(
    50
) NOT NULL,

    last_name VARCHAR
(
    50
) NOT NULL,
    password VARCHAR
(
    255
) NOT NULL,
    role VARCHAR
(
    255
) NOT NULL
    );

-- INSERT INTO todo (id, name, description, done, duration)
-- VALUES (1, 'Todo Number 1', 'Todo Description 1', false, 1);
--
-- INSERT INTO todo (id, name, description, done, duration)
-- VALUES (2, 'Todo Number 2', 'Todo Description 2', true, 2);
--
-- INSERT INTO todo (id, name, description, done, duration)
-- VALUES (3, 'Todo Number 3', 'Todo Description 3', false, 3);
--
-- INSERT INTO todo (id, name, description, done, duration)
-- VALUES (4, 'Todo Number 4', 'Todo Description 4', true, 4);

select *
from todo;