CREATE TABLE IF NOT EXISTS rjdesenvolvimento.address
(
    id           uuid         not null,
    internal_Id  bigserial    not null,
    active       boolean      not null,
    country      varchar(120) not null,
    state        varchar(120) not null,
    city         varchar(120) not null,
    zip_code     varchar(15)  not null,
    district     varchar(120) not null,
    public_place varchar(120) not null,
    number       varchar(20)  not null,
    complement   varchar(120) not null,
    person_fk    uuid         not null
);

CREATE UNIQUE INDEX address_id_uindex ON rjdesenvolvimento.address (id);
CREATE UNIQUE INDEX address_internalId_uindex ON rjdesenvolvimento.address (internal_Id);

ALTER TABLE rjdesenvolvimento.address
    ADD CONSTRAINT address_pk PRIMARY KEY (id);

ALTER TABLE rjdesenvolvimento.address
    ADD CONSTRAINT contract_person_fk FOREIGN KEY (person_fk) REFERENCES
        rjdesenvolvimento.person (id) ON DELETE CASCADE;
