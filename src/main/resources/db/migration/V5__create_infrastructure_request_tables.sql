CREATE SEQUENCE request_reference_code START WITH 1 INCREMENT BY 1;

CREATE TABLE request
(
    id                            UUID           NOT NULL DEFAULT uuidv7(),
    health_facility_id            UUID           NOT NULL,
    reference_code                VARCHAR(20)    NOT NULL UNIQUE,
    requester_name                VARCHAR(100)   NOT NULL,
    requester_email               VARCHAR(100)   NOT NULL,
    requester_phone_number        VARCHAR(8)     NOT NULL,
    requester_official_position   VARCHAR(50)    NOT NULL,
    requester_functional_position VARCHAR(50)    NOT NULL,
    rooms_to_expand               SMALLINT       NOT NULL,
    aproximate_cost               NUMERIC(10, 2) NOT NULL,
    notes                         TEXT,
    status                        VARCHAR(15)    NOT NULL CHECK (status IN ('TO_DO', 'IN_PROGRESS', 'DONE')),
    requested_date                DATE           NOT NULL,
    created_at                    TIMESTAMPTZ     NOT NULL,
    updated_at                    TIMESTAMPTZ    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_health_facility_TO_request
        FOREIGN KEY (health_facility_id) REFERENCES health_facility (id)
);

CREATE TABLE request_log
(
    id              UUID      NOT NULL DEFAULT uuidv7(),
    user_account_id UUID      NOT NULL,
    request_id      UUID      NOT NULL,
    description     TEXT      NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_user_account_TO_request_log
        FOREIGN KEY (user_account_id) REFERENCES user_account (id),
    CONSTRAINT FK_request_TO_request_log
        FOREIGN KEY (request_id) REFERENCES request (id)
);

CREATE TABLE request_element
(
    id                   UUID        NOT NULL DEFAULT uuidv7(),
    request_id           UUID        NOT NULL,
    building_element_id  UUID        NOT NULL,
    building_material_id UUID,
    required_action      VARCHAR(20) NOT NULL CHECK (required_action IN ('MAINTENANCE', 'REPLACEMENT')),
    created_at           TIMESTAMPTZ   NOT NULL,
    updated_at           TIMESTAMPTZ  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_request_TO_request_element
        FOREIGN KEY (request_id) REFERENCES request (id),
    CONSTRAINT FK_building_element_TO_request_element
        FOREIGN KEY (building_element_id) REFERENCES building_element (id),
    CONSTRAINT FK_building_material_TO_request_element
        FOREIGN KEY (building_material_id) REFERENCES building_material (id)
);

CREATE TABLE request_element_image
(
    id                 UUID        NOT NULL DEFAULT uuidv7(),
    request_element_id UUID        NOT NULL,
    path               VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (id),
    CONSTRAINT FK_request_element_TO_request_element_image
        FOREIGN KEY (request_element_id) REFERENCES request_element (id)
);

CREATE TABLE request_comment
(
    id              UUID      NOT NULL DEFAULT uuidv7(),
    user_account_id UUID      NOT NULL,
    request_id      UUID      NOT NULL,
    content         TEXT      NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_user_account_TO_request_comment
        FOREIGN KEY (user_account_id) REFERENCES user_account (id),
    CONSTRAINT FK_request_TO_request_comment
        FOREIGN KEY (request_id) REFERENCES request (id)
);