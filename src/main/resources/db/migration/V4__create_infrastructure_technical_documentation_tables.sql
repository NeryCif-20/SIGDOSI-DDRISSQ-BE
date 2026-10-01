CREATE TABLE plan_type
(
    id          UUID        NOT NULL DEFAULT uuidv7(),
    code        VARCHAR(10) NOT NULL UNIQUE,
    name        VARCHAR(35) NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE plan
(
    id           UUID        NOT NULL,
    plan_type_id UUID        NOT NULL,
    health_facility_id  UUID        NOT NULL,
    path     VARCHAR(45) NOT NULL UNIQUE,
    version      SMALLINT NOT NULL,
    notes       TEXT,
    created_at   TIMESTAMPTZ NOT NULL,
    updated_at   TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_plan_type_TO_property_plan
        FOREIGN KEY (plan_type_id) REFERENCES plan_type (id),
    CONSTRAINT FK_health_facility_TO_property_plan
        FOREIGN KEY (health_facility_id) REFERENCES health_facility (id),
    CONSTRAINT UQ_plan_type_health_facility_version
        UNIQUE (plan_type_id, health_facility_id, version)
);

CREATE TABLE facility_element
(
    id                   UUID        NOT NULL DEFAULT uuidv7(),
    health_facility_id          UUID        NOT NULL,
    building_element_id  UUID        NOT NULL,
    building_material_id UUID,
    created_at           TIMESTAMPTZ NOT NULL,
    updated_at           TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_health_facility_TO_facility_element
        FOREIGN KEY (health_facility_id) REFERENCES health_facility (id),
    CONSTRAINT FK_building_element_TO_facility_element
        FOREIGN KEY (building_element_id) REFERENCES building_element (id),
    CONSTRAINT FK_building_material_TO_facility_element
        FOREIGN KEY (building_material_id) REFERENCES building_material (id),
    CONSTRAINT UQ_health_facility_building_element
        UNIQUE (health_facility_id, building_element_id)
);