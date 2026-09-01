CREATE TABLE building_element
(
    id                    UUID        NOT NULL,
    name                  VARCHAR(50) NOT NULL UNIQUE,
    has_building_material BOOLEAN     NOT NULL,
    created_at            TIMESTAMPTZ NOT NULL,
    updated_at            TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE building_material
(
    id         UUID        NOT NULL,
    code       VARCHAR(10) NOT NULL UNIQUE,
    name       VARCHAR(35) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id)
);