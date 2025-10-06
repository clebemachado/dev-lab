-- Papéis (roles)
CREATE TABLE role (
    id VARCHAR(100) PRIMARY KEY
);

-- Operações (authorities/permissões)
CREATE TABLE operation (
    id VARCHAR(100) PRIMARY KEY
);

-- Relação N:N entre usuários e roles
-- user_roles compatível com users.id
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_u_r FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_u_r FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Relação N:N entre roles e operations
CREATE TABLE role_operations (
    role_id VARCHAR(100) NOT NULL,
    operation_id VARCHAR(100) NOT NULL,
    PRIMARY KEY (role_id, operation_id),
    CONSTRAINT fk_role_r_o FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    CONSTRAINT fk_operation_r_o FOREIGN KEY (operation_id) REFERENCES operation(id) ON DELETE CASCADE
);

-- ===============================
-- Inserindo roles
-- ===============================
INSERT INTO role (id) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- ===============================
-- Inserindo operations
-- ===============================
INSERT INTO operation (id) VALUES
('USER_READ'),
('USER_CREATE'),
('ORDER_READ'),
('ORDER_CREATE');

INSERT INTO role_operations (role_id, operation_id) VALUES
('ROLE_ADMIN', 'USER_READ'),
('ROLE_ADMIN', 'USER_CREATE'),
('ROLE_ADMIN', 'ORDER_READ'),
('ROLE_ADMIN', 'ORDER_CREATE');

-- ===============================
-- Se quiser, vincular ROLE_USER a algumas operações
-- ===============================
INSERT INTO role_operations (role_id, operation_id) VALUES
('ROLE_USER', 'USER_READ'),
('ROLE_USER', 'ORDER_READ');

