DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS companies;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq AS INTEGER START WITH 1000;

CREATE TABLE companies
(
    id                      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name                    VARCHAR(255)     NOT NULL,
    tin                     VARCHAR(10)      NOT NULL,
    address                 VARCHAR(255)     NOT NULL,
    phone_number            VARCHAR(12)     NOT NULL
);
CREATE UNIQUE INDEX companies_unique_idx
    ON companies (name);

CREATE TABLE employees
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name            VARCHAR(255)    NOT NULL,
    birth_date      DATE            NOT NULL,
    email           VARCHAR(255)    NOT NULL,
    company_id      INTEGER         NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX employees_unique_idx
    ON employees (company_id, name);