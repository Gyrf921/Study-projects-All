-- Ну тут всё очевидно, я немного меняю конфигурирующие запросы, потому что pgAdmin4 ругается, а я в нём всё кручу

Create table If Not Exists Employee (Id int, Name varchar(255), Salary int, ManagerId int);

Truncate table Employee;
insert into Employee (Id, Name, Salary, ManagerId) values (1, 'Joe', '70000', 3);
insert into Employee (Id, Name, Salary, ManagerId) values (2, 'Henry', '80000', 4);
insert into Employee (Id, Name, Salary, ManagerId) values (3, 'Sam', '60000', null);
insert into Employee (Id, Name, Salary, ManagerId) values (4, 'Max', '90000', null);


SELECT e1.Name
FROM Employee e1
         INNER JOIN Employee e2 ON e1.ManagerId = e2.Id
WHERE e1.Salary > e2.Salary;