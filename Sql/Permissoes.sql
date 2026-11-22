CREATE TABLE permissao (
    id VARCHAR(64) PRIMARY KEY,
    nome VARCHAR(128) UNIQUE NOT NULL
);

INSERT INTO permissao(id, nome) VALUES 
('fornecedores', 'Fornecedores'),
('vendas', 'Vendas'),
('clientes', 'Clientes'),
('estoque', 'Estoque'),
('usuarios', 'Usuários');
DELETE FROM permissao AS p WHERE p.id = 'cadastro_clientes' OR p.id =  'cadastro_estoque'

SELECT * FROM permissao;