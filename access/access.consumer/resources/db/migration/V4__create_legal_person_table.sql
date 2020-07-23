CREATE TABLE IF NOT EXISTS rjdesenvolvimento.legal_person
(
    id               uuid         not null,
    internal_Id      bigserial    not null,
    active           boolean      not null,
    company_name     varchar(120) not null,
    fancy_name       varchar(120) not null,
    document         varchar(30)  not null,
    opening_date     date         not null,
    legal_nature     varchar(15)  not null,
    line_of_business varchar(120) not null,
    person_fk        uuid         not null
);

CREATE UNIQUE INDEX legal_person_id_uindex ON rjdesenvolvimento.legal_person (id);
CREATE UNIQUE INDEX legal_person_internalId_uindex ON rjdesenvolvimento.legal_person (internal_Id);
CREATE UNIQUE INDEX legal_person_fancy_name_index ON rjdesenvolvimento.legal_person (fancy_name);
CREATE UNIQUE INDEX legal_person_document_index ON rjdesenvolvimento.legal_person (document);

ALTER TABLE rjdesenvolvimento.legal_person
    ADD CONSTRAINT legal_person_pk PRIMARY KEY (id);

ALTER TABLE rjdesenvolvimento.legal_person
    ADD CONSTRAINT legal_person_person_fk FOREIGN KEY (person_fk) REFERENCES
        rjdesenvolvimento.person (id) ON DELETE CASCADE;
