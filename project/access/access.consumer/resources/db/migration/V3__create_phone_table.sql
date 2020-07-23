CREATE TABLE IF NOT EXISTS rjdesenvolvimento.phone
(
    id          uuid        not null,
    internal_Id bigserial   not null,
    active      boolean     not null,
    idd         varchar(10) not null,
    ddd         varchar(10) not null,
    number      varchar(30) not null,
    person_fk   uuid
);

CREATE UNIQUE INDEX phone_id_uindex ON rjdesenvolvimento.phone (id);
CREATE UNIQUE INDEX phone_internalId_uindex ON rjdesenvolvimento.phone (internal_Id);
CREATE UNIQUE INDEX phone_number_index ON rjdesenvolvimento.phone (number);


ALTER TABLE rjdesenvolvimento.phone
    ADD CONSTRAINT phone_pk PRIMARY KEY (id);

ALTER TABLE rjdesenvolvimento.phone
    ADD CONSTRAINT phone_person_fk FOREIGN KEY (person_fk) REFERENCES
        rjdesenvolvimento.person (id) ON DELETE CASCADE;
