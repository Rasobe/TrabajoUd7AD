
# LA CONTRASEÑA DE LOS USUARIOS ES EL MISMO USERNAME --> unsername: admin, password: admin

INSERT INTO usuarios (imagen, email, username, password, role, enabled) VALUES ('username.png', 'admin@admin.com', 'admin', '$2a$10$uS1OFPT/sR8UV8HDzL8zyenqm7cUQ6wkjonbbPR5zENOorjVGNF.a', 'ROLE_ADMIN', 1);
INSERT INTO usuarios (imagen, email, username, password, role, enabled) VALUES ('username.png', 'user@user.com', 'user', '$2a$10$/AaPEMMbgw81p24.vW/AC.dm7ltuD3pDL.N25LD3XMixKmhUdiY0i', 'ROLE_USER', 1);
INSERT INTO usuarios (imagen, email, username, password, role, enabled) VALUES ('username.png', 'prueba@prueba.com', 'prueba', '$2a$10$nezGNBwUn1.lkbY3E0rxEuwao9GQIx2YfF1yssrnTGDO2U1Tf40g6', 'ROLE_USER', 1);

INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('DAM1', 'DAM1 description', '2023-03-30 00:00:00.000000', 30, 1);
INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('DAM2', 'DAM2 description', '2023-09-01 00:00:00.000000', 30, 2);
INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('DAW1', 'DAW1 description', '2024-03-11 00:00:00.000000', 30, 2);
INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('DAW2', 'DAW2 description', '2024-06-03 00:00:00.000000', 30, 1);
INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('SIR1', 'SIR1 description', '2025-03-25 00:00:00.000000', 30, 3);
INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('SIR2', 'SIR2 description', '2023-03-05 00:00:00.000000', 30, 3);
INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('CYBER1', 'CYBER1 description', '2023-12-04 00:00:00.000000', 30, 1);
INSERT INTO cursos (titulo, descripcion, fecha_inicio, horas, usuario_curso) VALUES ('CYBER2', 'CYBER2 description', '2023-01-03 00:00:00.000000', 30, 1);