-- Script de migração para popular o banco de dados do PetShop
-- Autor: Yan Werlley
-- Data: 2025-06-28

-- Limpeza das tabelas (em ordem para respeitar as constraints)
DELETE FROM vacina;
DELETE FROM consulta_exame;
DELETE FROM consulta_medicamento;
DELETE FROM consulta;
DELETE FROM exame_consulta;
DELETE FROM medicamento_consulta;
DELETE FROM agendamento;
DELETE FROM animal;
DELETE FROM usuario_perfil WHERE perfil_id != 1;
DELETE FROM cliente;
DELETE FROM endereco;
DELETE FROM exame WHERE id > 5;
DELETE FROM medicamento WHERE id > 5;

-- Inserção de endereços
INSERT INTO endereco (id, rua, numero, complemento, bairro, cidade, estado, cep) VALUES 
(1, 'Rua das Flores', '123', 'Apto 101', 'Centro', 'São Paulo', 'SP', '01234-567'),
(2, 'Avenida Brasil', '456', NULL, 'Jardim América', 'Rio de Janeiro', 'RJ', '21040-361'),
(3, 'Rua dos Pinheiros', '789', 'Casa 2', 'Pinheiros', 'São Paulo', 'SP', '05422-030'),
(4, 'Avenida Paulista', '1000', 'Sala 45', 'Bela Vista', 'São Paulo', 'SP', '01310-100'),
(5, 'Rua Voluntários da Pátria', '345', NULL, 'Botafogo', 'Rio de Janeiro', 'RJ', '22270-000');

-- Inserção de clientes
INSERT INTO cliente (id, nome, cpf, telefone, data_nascimento, email, endereco_id) VALUES 
(1, 'João Silva', '123.456.789-01', '(11) 98765-4321', '1980-05-15', 'joao.silva@email.com', 1),
(2, 'Maria Oliveira', '987.654.321-09', '(21) 91234-5678', '1992-10-20', 'maria.oliveira@email.com', 2),
(3, 'Carlos Santos', '456.789.123-45', '(11) 95555-9999', '1975-03-08', 'carlos.santos@email.com', 3),
(4, 'Ana Pereira', '789.123.456-78', '(11) 94444-3333', '1988-12-25', 'ana.pereira@email.com', 4),
(5, 'Roberto Almeida', '321.654.987-00', '(21) 93333-2222', '1970-07-30', 'roberto.almeida@email.com', 5);

-- Inserção de usuários para clientes (senha: cliente123)
INSERT INTO usuario (id, email, nome, senha, ativo) VALUES 
(2, 'joao.silva@email.com', 'João Silva', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true),
(3, 'maria.oliveira@email.com', 'Maria Oliveira', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true),
(4, 'carlos.santos@email.com', 'Carlos Santos', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true),
(5, 'ana.pereira@email.com', 'Ana Pereira', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true),
(6, 'roberto.almeida@email.com', 'Roberto Almeida', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true);

-- Associação dos usuários ao perfil CLIENTE
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES 
(2, 4),
(3, 4),
(4, 4),
(5, 4),
(6, 4);

-- Inserção de usuários funcionários (senha: petshop123)
INSERT INTO usuario (id, email, nome, senha, ativo) VALUES 
(7, 'dr.pedro@petshop.com', 'Dr. Pedro Mendes', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true),
(8, 'dra.julia@petshop.com', 'Dra. Julia Costa', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true),
(9, 'recep.amanda@petshop.com', 'Amanda Souza', '$2a$10$qyFeObrYFcBNHIt7B9HUJeWQB5lhrGW9TQrIra1PxpuFSNsXZIWIK', true);

-- Associação dos usuários funcionários aos perfis
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES 
(7, 2), -- Dr. Pedro como VETERINARIO
(8, 2), -- Dra. Julia como VETERINARIO
(9, 3); -- Amanda como RECEPCIONISTA

-- Inserção de animais (cachorros)
INSERT INTO animal (id, nome, peso, cor, possui_doenca, tipo, proprietario_id, dtype, raca, adestrado, castrado) VALUES 
(1, 'Rex', 15.5, 'Marrom', false, 'Cachorro', 1, 'CACHORRO', 'Labrador', true, null),
(2, 'Thor', 8.2, 'Preto', true, 'Cachorro', 2, 'CACHORRO', 'Bulldog', false, null),
(3, 'Max', 12.0, 'Dourado', false, 'Cachorro', 3, 'CACHORRO', 'Golden Retriever', true, null),
(4, 'Toby', 5.5, 'Branco', false, 'Cachorro', 4, 'CACHORRO', 'Poodle', false, null),
(5, 'Duke', 20.0, 'Marrom e Preto', false, 'Cachorro', 5, 'CACHORRO', 'Pastor Alemão', true, null);

-- Inserção de animais (gatos)
INSERT INTO animal (id, nome, peso, cor, possui_doenca, tipo, proprietario_id, dtype, raca, adestrado, castrado) VALUES 
(6, 'Luna', 4.3, 'Branco', false, 'Gato', 1, 'GATO', 'Siamês', null, true),
(7, 'Felix', 5.1, 'Cinza', false, 'Gato', 3, 'GATO', 'Persa', null, false),
(8, 'Mia', 3.8, 'Preto', true, 'Gato', 2, 'GATO', 'Angorá', null, true),
(9, 'Simba', 4.5, 'Laranja', false, 'Gato', 4, 'GATO', 'Maine Coon', null, false),
(10, 'Nina', 3.2, 'Malhado', false, 'Gato', 5, 'GATO', 'Ragdoll', null, true);

-- Inserção de exames
INSERT INTO exame (id, nome, descricao, valor) VALUES 
(1, 'Hemograma', 'Exame de sangue completo', 80.00),
(2, 'Raio-X', 'Radiografia para diagnóstico', 120.00),
(3, 'Ultrassonografia', 'Exame de imagem por ultrassom', 150.00),
(4, 'Exame de Fezes', 'Análise parasitológica', 50.00),
(5, 'Exame de Urina', 'Análise bioquímica da urina', 60.00);

-- Inserção de medicamentos
INSERT INTO medicamento (id, nome, descricao, valor, dosagem) VALUES 
(1, 'Antibiótico', 'Para tratamento de infecções', 45.00, '1 comprimido a cada 12h'),
(2, 'Anti-inflamatório', 'Para redução de inflamações', 35.00, '1 comprimido ao dia'),
(3, 'Vermífugo', 'Para controle de vermes intestinais', 25.00, 'Dose única'),
(4, 'Antipulgas', 'Para controle de pulgas e carrapatos', 60.00, 'Aplicação mensal'),
(5, 'Vitaminas', 'Suplemento vitamínico', 30.00, '1 comprimido ao dia');

-- Inserção de perfis (roles)
INSERT INTO perfil (id, nome, descricao) VALUES
(1, 'ADMIN', 'Administrador do sistema'),
(2, 'VETERINARIO', 'Médico veterinário'),
(3, 'RECEPCIONISTA', 'Recepcionista do petshop'),
(4, 'CLIENTE', 'Cliente do petshop');

-- Inserção de consultas
INSERT INTO consulta (id, data_hora, motivo, diagnostico, tratamento, observacoes, status, animal_id, veterinario_id) VALUES
(1, '2025-06-15 10:00:00', 'Checkup de rotina', 'Animal saudável', 'Nenhum tratamento necessário', 'Manter vacinação em dia', 'REALIZADA', 1, 7),
(2, '2025-06-16 14:30:00', 'Vômito e diarreia', 'Gastroenterite', 'Antibiótico e dieta especial', 'Retornar em 7 dias', 'REALIZADA', 2, 8),
(3, '2025-06-17 09:15:00', 'Ferimento na pata', 'Corte superficial', 'Limpeza e curativo', 'Trocar curativo diariamente', 'REALIZADA', 3, 7),
(4, '2025-06-18 16:00:00', 'Perda de apetite', 'Estresse', 'Suplemento vitamínico', 'Monitorar alimentação', 'REALIZADA', 6, 8),
(5, '2025-06-20 11:30:00', 'Tosse persistente', 'Traqueobronquite', 'Antibiótico e anti-inflamatório', 'Manter em ambiente aquecido', 'REALIZADA', 4, 7),
(6, '2025-07-01 10:00:00', 'Vacinação', 'N/A', 'Aplicação de vacina', 'N/A', 'AGENDADA', 5, 8),
(7, '2025-07-02 15:45:00', 'Castração', 'N/A', 'Procedimento cirúrgico', 'Jejum de 8 horas', 'AGENDADA', 9, 7),
(8, '2025-07-03 09:30:00', 'Checkup pós-operatório', 'N/A', 'Avaliação de cicatrização', 'N/A', 'AGENDADA', 8, 8);

-- Inserção de exames realizados
INSERT INTO exame_realizado (id, nome, tipo, resultado, data_realizacao, data_resultado, observacoes, consulta_id, animal_id) VALUES
(1, 'Hemograma Completo', 'Sangue', 'Normal', '2025-06-15', '2025-06-16', 'Todos os parâmetros dentro da normalidade', 1, 1),
(2, 'Raio-X Torácico', 'Imagem', 'Normal', '2025-06-16', '2025-06-16', 'Sem alterações visíveis', 2, 2),
(3, 'Exame de Fezes', 'Parasitológico', 'Presença de parasitas', '2025-06-17', '2025-06-18', 'Identificado Giardia', 3, 3),
(4, 'Ultrassom Abdominal', 'Imagem', 'Alterado', '2025-06-18', '2025-06-19', 'Leve inflamação intestinal', 4, 6),
(5, 'Exame de Urina', 'Bioquímico', 'Normal', '2025-06-20', '2025-06-21', 'Sem alterações significativas', 5, 4);

-- Inserção de medicamentos prescritos
INSERT INTO medicamento_prescrito (id, nome, dosagem, frequencia, instrucoes, data_inicio, data_fim, observacoes, consulta_id, animal_id) VALUES
(1, 'Amoxicilina', '50mg', '12/12h', 'Administrar com alimento', '2025-06-15', '2025-06-25', 'Completar o tratamento mesmo com melhora dos sintomas', 2, 2),
(2, 'Prednisolona', '10mg', '24h', 'Administrar pela manhã', '2025-06-16', '2025-06-23', 'Reduzir a dose gradualmente', 2, 2),
(3, 'Metronidazol', '25mg', '12/12h', 'Administrar após alimentação', '2025-06-18', '2025-06-28', 'Pode causar alteração no sabor dos alimentos', 3, 3),
(4, 'Dipirona', '1 gota/kg', '8/8h', 'Diluir em água', '2025-06-20', '2025-06-23', 'Suspender em caso de vômito', 5, 4),
(5, 'Suplemento Vitamínico', '1ml', '24h', 'Misturar na ração', '2025-06-18', '2025-07-18', 'Manter refrigerado após aberto', 4, 6);

-- Inserção de vacinas
INSERT INTO vacina (id, nome, data_aplicacao, lote, data_validade, data_proxima_dose, animal_id) VALUES 
(1, 'V8', '2025-01-15', 'L123456', '2026-01-15', '2025-07-15', 1),
(2, 'Antirrábica', '2025-02-10', 'L789012', '2026-02-10', null, 1),
(3, 'V4', '2025-03-05', 'L345678', '2026-03-05', '2025-09-05', 3),
(4, 'Antirrábica', '2025-03-20', 'L901234', '2026-03-20', null, 4),
(5, 'Triplice Felina', '2025-01-10', 'L567890', '2026-01-10', '2025-07-10', 6),
(6, 'Antirrábica', '2025-02-15', 'L234567', '2026-02-15', null, 7),
(7, 'Triplice Felina', '2025-03-10', 'L345678', '2026-03-10', '2025-09-10', 8),
(8, 'V8', '2025-02-20', 'L456789', '2026-02-20', '2025-08-20', 2),
(9, 'V10', '2025-01-25', 'L567890', '2026-01-25', '2025-07-25', 5),
(10, 'Antirrábica', '2025-04-05', 'L678901', '2026-04-05', null, 9);

-- Inserção de agendamentos
INSERT INTO agendamento (id, data_hora, motivo, observacoes, animal_id, status) VALUES 
(1, '2025-07-01 10:00:00', 'Consulta de rotina', 'Primeira consulta do animal', 1, 'AGENDADO'),
(2, '2025-07-02 14:30:00', 'Vacinação', 'Dose de reforço V8', 1, 'AGENDADO'),
(3, '2025-07-03 09:00:00', 'Consulta de emergência', 'Animal com sintomas de gripe', 2, 'AGENDADO'),
(4, '2025-07-04 11:00:00', 'Exame de sangue', 'Verificar anemia', 6, 'AGENDADO'),
(5, '2025-07-05 16:00:00', 'Consulta de rotina', 'Checkup anual', 3, 'AGENDADO'),
(6, '2025-06-15 10:00:00', 'Consulta de rotina', 'Checkup semestral', 4, 'CONCLUIDO'),
(7, '2025-06-16 14:00:00', 'Vacinação', 'Antirrábica', 7, 'CONCLUIDO'),
(8, '2025-06-17 09:30:00', 'Consulta de emergência', 'Animal com diarreia', 5, 'CONCLUIDO'),
(9, '2025-06-18 11:30:00', 'Exame de sangue', 'Verificar infecção', 8, 'CONCLUIDO'),
(10, '2025-06-19 15:30:00', 'Consulta de rotina', 'Checkup anual', 9, 'CONCLUIDO');

-- Inserção de consultas para agendamentos concluídos
INSERT INTO consulta (id, data_hora_inicio, data_hora_fim, diagnostico, observacoes, agendamento_id) VALUES 
(1, '2025-06-15 10:00:00', '2025-06-15 10:30:00', 'Animal saudável', 'Manter alimentação balanceada', 6),
(2, '2025-06-16 14:00:00', '2025-06-16 14:15:00', 'Vacinação realizada com sucesso', 'Próxima dose em 1 ano', 7),
(3, '2025-06-17 09:30:00', '2025-06-17 10:00:00', 'Gastroenterite leve', 'Prescrição de medicamentos e dieta especial', 8),
(4, '2025-06-18 11:30:00', '2025-06-18 12:00:00', 'Infecção bacteriana leve', 'Iniciar antibiótico', 9),
(5, '2025-06-19 15:30:00', '2025-06-19 16:00:00', 'Animal saudável', 'Manter cuidados atuais', 10);

-- Associação de exames às consultas
INSERT INTO consulta_exame (consulta_id, exame_id) VALUES 
(1, 1), -- Hemograma para consulta 1
(3, 4), -- Exame de Fezes para consulta 3
(4, 1), -- Hemograma para consulta 4
(4, 5); -- Exame de Urina para consulta 4

-- Associação de medicamentos às consultas
INSERT INTO consulta_medicamento (consulta_id, medicamento_id) VALUES 
(3, 1), -- Antibiótico para consulta 3
(3, 2), -- Anti-inflamatório para consulta 3
(4, 1), -- Antibiótico para consulta 4
(5, 5); -- Vitaminas para consulta 5
