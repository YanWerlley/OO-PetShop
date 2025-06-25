-- Inserção dos perfis básicos
INSERT INTO perfil (id, nome, descricao) VALUES (1, 'ADMIN', 'Administrador do sistema') ON DUPLICATE KEY UPDATE nome = nome;
INSERT INTO perfil (id, nome, descricao) VALUES (2, 'VETERINARIO', 'Veterinário do PetShop') ON DUPLICATE KEY UPDATE nome = nome;
INSERT INTO perfil (id, nome, descricao) VALUES (3, 'RECEPCIONISTA', 'Recepcionista do PetShop') ON DUPLICATE KEY UPDATE nome = nome;
INSERT INTO perfil (id, nome, descricao) VALUES (4, 'CLIENTE', 'Cliente do PetShop') ON DUPLICATE KEY UPDATE nome = nome;

-- Inserção de um usuário administrador padrão (senha: admin123)
INSERT INTO usuario (id, email, nome, senha, ativo) 
VALUES (1, 'admin@petshop.com', 'Administrador', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true) 
ON DUPLICATE KEY UPDATE email = email;

-- Associação do usuário administrador ao perfil ADMIN
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES (1, 1) ON DUPLICATE KEY UPDATE usuario_id = usuario_id;
