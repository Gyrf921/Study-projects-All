Create table If Not Exists Person (Id int, Email varchar(255));

Truncate table Person;
insert into Person (Id, Email) values (1, 'a@b.com');
insert into Person (Id, Email) values (2, 'c@d.com');
insert into Person (Id, Email) values (3, 'a@b.com');


SELECT p.Email
FROM Person p
GROUP BY Email
HAVING COUNT((Email)) > 1;