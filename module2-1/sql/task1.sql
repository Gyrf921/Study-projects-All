-- В постгрессе нет функции NVL(), как в Оракле, так что пришлось использовать аналог.
CREATE TABLE Person (
                        PersonId INT,
                        FirstName VARCHAR(255),
                        LastName VARCHAR(255)
);

CREATE TABLE Address (
                         AddressId INT,
                         PersonId INT,
                         City VARCHAR(255),
                         State VARCHAR(255)
);

TRUNCATE TABLE Person;
INSERT INTO Person (PersonId, LastName, FirstName) VALUES (1, 'Wang', 'Allen');

TRUNCATE TABLE Address;
INSERT INTO Address (AddressId, PersonId, City, State) VALUES (1, 2, 'New York City', 'New York');


SELECT p.FirstName, p.LastName, COALESCE(a.City, ' ') AS City, COALESCE(a.State, ' ') AS State
FROM Person p
LEFT JOIN Address a ON a.PersonId = p.PersonId;