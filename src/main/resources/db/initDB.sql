DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS employees;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;

CREATE TABLE companies
(
    id                      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name                    VARCHAR     NOT NULL,
    tin                     INTEGER     NOT NULL,
    address                 VARCHAR     NOT NULL,
    phone_number            TEXT        NOT NULL
);
CREATE UNIQUE INDEX products_unique_idx
    ON companies (name);

CREATE TABLE employees
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name            VARCHAR     NOT NULL,
    birth_date      TIMESTAMP   NOT NULL,
    email           VARCHAR     NOT NULL,
    company_id      INTEGER     NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX articles_unique_idx
    ON employees (company_id, name);