INSERT INTO usuarios (username, password, email, enabled) VALUES ('usuario1', 'usuario1', 'usuario1@example.com', true)
INSERT INTO roles (id, role_name) VALUES (1, 'ROLE_USER')


INSERT INTO usuario_roles (roles, usuarios_id)
VALUES ([ROLE_USER], (SELECT user_id FROM usuarios WHERE username = 'usuario1'));