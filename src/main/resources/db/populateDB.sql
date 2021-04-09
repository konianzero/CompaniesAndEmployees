DELETE FROM employees;
DELETE FROM companies;
ALTER SEQUENCE global_seq RESTART WITH 1000;

INSERT INTO companies (name, tin, address, phone_number)
VALUES ('Компания_1', '1234123412', 'Улица 1', '+79005554466'),
       ('Компания_2', '5678567856', 'Проспект 2', '+75008887799');

INSERT INTO employees (name, birth_date, email, company_id)
VALUES ('Иванов Иван Иванович', '2000-01-01', 'ivan@mail.com', 1000),
       ('Петров Петр Петрович', '2000-02-02', 'petr@mail.com', 1001);