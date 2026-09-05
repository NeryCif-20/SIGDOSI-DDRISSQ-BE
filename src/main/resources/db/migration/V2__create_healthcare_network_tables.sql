CREATE TABLE dms
(
    id         UUID        NOT NULL,
    name       VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE riss
(
    id         UUID        NOT NULL,
    dms_id     UUID        NOT NULL,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
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
    territory  SMALLINT    NOT NULL CHECK (territory >= 0),
    sector     CHAR(1)     NOT NULL,
    population BIGINT      NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_riss_TO_community
        FOREIGN KEY (riss_id) REFERENCES riss (id),
    CONSTRAINT UQ_riss_name_territory_sector
        UNIQUE (riss_id, name, territory, sector)
);

CREATE TABLE health_facility_type
(
    id         UUID        NOT NULL,
    code       VARCHAR(10) NOT NULL UNIQUE,
    name       VARCHAR(25) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);

CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE health_facility
(
    id                       UUID           NOT NULL,
    community_id             UUID           NOT NULL,
    health_facility_type_id  UUID           NOT NULL,
    is_headquarters          BOOLEAN        NOT NULL,
    status                   VARCHAR(20)    NOT NULL CHECK (status IN ('ACTIVE', 'UNDER_MAINTENANCE', 'INACTIVE')),
    total_land_area          NUMERIC(10, 2) NOT NULL CHECK (total_land_area >= 0 AND building_footprint + available_expansion_area <= total_land_area),
    building_footprint       NUMERIC(10, 2) NOT NULL CHECK (building_footprint >= 0),
    available_expansion_area NUMERIC(10, 2) NOT NULL CHECK (available_expansion_area >= 0),
    property_tenure          VARCHAR(25)    NOT NULL CHECK (property_tenure IN ('OWN', 'MUNICIPAL', 'COMMUNITY', 'PRIVATE')),
    property_status          VARCHAR(15)    NOT NULL CHECK (property_status IN
                                                            ('REGISTERED', 'DONATED', 'ASSIGNED', 'ON_LOAN', 'LEASED')),
    location                 GEOGRAPHY(point, 4326),
    property_notes           TEXT,
    created_at               TIMESTAMPTZ    NOT NULL,
    updated_at               TIMESTAMPTZ    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_community_TO_health_facility
        FOREIGN KEY (community_id) REFERENCES community (id),
    CONSTRAINT FK_health_facility_type_TO_health_facility
        FOREIGN KEY (health_facility_type_id) REFERENCES health_facility_type (id)
);