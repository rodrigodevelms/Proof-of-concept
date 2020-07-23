CREATE TABLE IF NOT EXISTS rjdesenvolvimento.contract
(
    id               uuid         not null,
    internal_Id      bigserial    not null,
    active           boolean      not null,
    work_regime      varchar(15)  not null,
    hiring_date      date         not null,
    resignation_date date,
    remuneration     decimal      not null,
    role             varchar(120) not null,
    workday          varchar(15)  not null,
    employee_fk      uuid         not null
);

CREATE UNIQUE INDEX contract_id_uindex ON rjdesenvolvimento.contract (id);
CREATE UNIQUE INDEX contract_internalId_uindex ON rjdesenvolvimento.contract (internal_Id);

ALTER TABLE rjdesenvolvimento.contract
    ADD CONSTRAINT contract_pk PRIMARY KEY (id);

ALTER TABLE rjdesenvolvimento.contract
    ADD CONSTRAINT contract_employee_fk FOREIGN KEY (employee_fk) REFERENCES
        rjdesenvolvimento.employee (id) ON DELETE CASCADE;
