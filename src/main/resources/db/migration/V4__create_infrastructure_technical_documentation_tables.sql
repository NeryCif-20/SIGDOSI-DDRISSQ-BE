CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE plan_type
(
    id          UUID        NOT NULL,
    code        VARCHAR(10) NOT NULL UNIQUE,
    name        VARCHAR(35) NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE property
(
    id                       UUID           NOT NULL,
    total_land_area          NUMERIC(10, 2) NOT NULL,
    building_footprint       NUMERIC(10, 2) NOT NULL,
    available_expansion_area NUMERIC(10, 2) NOT NULL,
    tenure                   VARCHAR(25)    NOT NULL CHECK (tenure IN ('OWN', 'MUNICIPAL', 'COMMUNITY', 'PRIVATE')),
    status                   VARCHAR(15)    NOT NULL CHECK (status IN ('REGISTERED', 'DONATED', 'ASSIGNED', 'ON-LOAN', 'LEASED')),
    notes                    TEXT,
    location                 GEOGRAPHY(point, 4326),
    created_at               TIMESTAMPTZ    NOT NULL,
    updated_at               TIMESTAMPTZ    NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE property_element
(
    id                   UUID        NOT NULL,
    property_id          UUID        NOT NULL,
    building_element_id  UUID        NOT NULL,
    building_material_id UUID,
    created_at           TIMESTAMPTZ NOT NULL,
    updated_at           TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_property_TO_property_component
        FOREIGN KEY (property_id) REFERENCES property (id),
    CONSTRAINT FK_building_element_TO_property_component
        FOREIGN KEY (building_element_id) REFERENCES building_element (id),
    CONSTRAINT FK_building_material_TO_property_component
        FOREIGN KEY (building_material_id) REFERENCES building_material (id),
    CONSTRAINT UQ_property_building_element
        UNIQUE (property_id, building_element_id)
);

CREATE TABLE property_picture
(
    id          UUID        NOT NULL,
    property_id UUID        NOT NULL,
    file_name   VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (id),
    CONSTRAINT FK_property_TO_property_picture
        FOREIGN KEY (property_id) REFERENCES property (id)
);

CREATE TABLE property_plan
(
    id           UUID        NOT NULL,
    plan_type_id UUID        NOT NULL,
    property_id  UUID        NOT NULL,
    file_name    VARCHAR(45) NOT NULL,
    version      SMALLINT,
    created_at   TIMESTAMPTZ NOT NULL,
    updated_at   TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_plan_type_TO_property_plan
        FOREIGN KEY (plan_type_id) REFERENCES plan_type (id),
    CONSTRAINT FK_property_TO_property_plan
        FOREIGN KEY (health_facility_id) REFERENCES health_facility (id),
    CONSTRAINT UQ_plan_type_property_version
        UNIQUE (plan_type_id, health_facility_id, version)
);