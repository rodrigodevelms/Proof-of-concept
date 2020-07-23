CREATE TABLE IF NOT EXISTS rjdesenvolvimento.person
(
    id             uuid         not null,
    internal_Id    bigserial    not null,
    active         boolean      not null,
    name           varchar(120) not null,
    email          varchar(120) not null,
    document       varchar(30)  not null,
    birthdate      date         not null,
    marital_status varchar(15)  not null,
    gender         varchar(15)  not null
);

CREATE UNIQUE INDEX person_id_uindex ON rjdesenvolvimento.person (id);
CREATE UNIQUE INDEX person_internalId_uindex ON rjdesenvolvimento.person (internal_Id);
CREATE UNIQUE INDEX person_email_index ON rjdesenvolvimento.person (email);
CREATE UNIQUE INDEX person_document_index ON rjdesenvolvimento.person (document);

ALTER TABLE rjdesenvolvimento.person
    ADD CONSTRAINT client_pk PRIMARY KEY (id);
