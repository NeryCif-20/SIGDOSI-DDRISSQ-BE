CREATE TABLE dms
(
    id         UUID        NOT NULL,
    name       VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE riss
(
    id         UUID        NOT NULL,
    dms_id     UUID        NOT NULL,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_dms_TO_riss
        FOREIGN KEY (dms_id) REFERENCES dms (id),
    CONSTRAINT UQ_dms_name
        UNIQUE (dms_id, name)
);

CREATE TABLE community
(
    id         UUID        NOT NULL,
    riss_id    UUID        NOT NULL,
    name       VARCHAR(50) NOT NULL,
    territory  SMALLINT    NOT NULL,
    sector     CHAR(1)  NOT NULL,
    population BIGINT      NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_riss_TO_community
        FOREIGN KEY (riss_id) REFERENCES riss (id),
    CONSTRAINT UQ_riss_name_territory_sector
        UNIQUE (riss_id, name, territory, sector)
);