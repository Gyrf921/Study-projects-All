-- У нас на проекте везде лефт джойны, так что я могу их уже по привычке использовать просто, но тут вроде уместно.

Create table If Not Exists Customers (Id int, Name varchar(255));
Create table If Not Exists Orders (Id int, CustomerId int);

Truncate table Customers;
insert into Customers (Id, Name) values ('1', 'Joe');
insert into Customers (Id, Name) values ('2', 'Henry');
insert into Customers (Id, Name) values ('3', 'Sam');
insert into Customers (Id, Name) values ('4', 'Max');

Truncate table Orders;
insert into Orders (Id, CustomerId) values ('1', '3');
insert into Orders (Id, CustomerId) values ('2', '1');

SELECT c.name
FROM Customers c
         LEFT JOIN  Orders o ON o.CustomerId = c.Id
where o.Id is null;