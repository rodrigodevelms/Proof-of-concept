CREATE TABLE IF NOT EXISTS rjdesenvolvimento.employee
(
    id          uuid      not null,
    internal_Id bigserial not null,
    active      boolean   not null,
    person_fk   uuid      not null
);

CREATE UNIQUE INDEX employee_id_uindex ON rjdesenvolvimento.employee (id);
CREATE UNIQUE INDEX employee_internalId_uindex ON rjdesenvolvimento.employee (internal_Id);

ALTER TABLE rjdesenvolvimento.employee
    ADD CONSTRAINT employee_pk PRIMARY KEY (id);

ALTER TABLE rjdesenvolvimento.employee
    ADD CONSTRAINT employee_person_fk FOREIGN KEY (person_fk) REFERENCES
        rjdesenvolvimento.person (id) ON DELETE CASCADE;
