-- Первый - красивее, второй полностью выполняет ТЗ

Create table If Not Exists Employee (Id int, Salary int);

TRUNCATE table Employee;
insert into Employee (Id, Salary) values ('1', '100');
insert into Employee (Id, Salary) values ('2', '200');
insert into Employee (Id, Salary) values ('3', '300');


-- Первый вариант
SELECT DISTINCT(Salary)
FROM Employee
ORDER BY Salary DESC
LIMIT 1 OFFSET 1;


-- Второй вариант
SELECT MAX(Salary)
FROM Employee
WHERE Salary < (SELECT MAX(Salary) FROM Employee);
