CREATE TABLE permissao (
    id VARCHAR(64) PRIMARY KEY,
    nome VARCHAR(128) UNIQUE NOT NULL
);

INSERT INTO permissao(id, nome) VALUES 
('vendas', 'Vendas'),
('clientes', 'Clientes'),
('cadastro_clientes', 'Cadastro de Clientes'),
('estoque', 'Estoque'),
('cadastro_estoque', 'Cadastro Estoque'),
('usuarios', 'Usuários');

SELECT * FROM permissao;