CREATE TABLE role
(
    id          UUID        NOT NULL,
    name        VARCHAR(30) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE permission
(
    id     UUID        NOT NULL,
    module VARCHAR(20) NOT NULL,
    action VARCHAR(10) NOT NULL CHECK (action IN ('CREATE', 'READ', 'UPDATE', 'DELETE')),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UQ_module_action
        UNIQUE (module, action)
);

CREATE TABLE role_permission
(
    role_id       UUID NOT NULL,
    permission_id UUID NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT FK_role_TO_role_permission
        FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT FK_permission_TO_role_permission
        FOREIGN KEY (permission_id) REFERENCES permission (id),
    CONSTRAINT UQ_role_permission
        UNIQUE (role_id, permission_id)
);

CREATE TABLE user_account
(
    id            UUID         NOT NULL,
    role_id       UUID         NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(75)  NOT NULL,
    status        VARCHAR(10)  NOT NULL CHECK (status IN ('ACTIVE', 'SUSPENDED', 'INACTIVE')),
    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_role_TO_user_account
        FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE user_profile
(
    id              UUID        NOT NULL,
    user_account_id UUID        NOT NULL UNIQUE,
    cui             VARCHAR(13) NOT NULL UNIQUE,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    avatar          VARCHAR(45) UNIQUE,
    phone_number    VARCHAR(8) UNIQUE,
    created_at      TIMESTAMP   NOT NULL,
    updated_at      TIMESTAMP   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_user_account_TO_user_profile
        FOREIGN KEY (user_account_id) REFERENCES user_account (id)
);