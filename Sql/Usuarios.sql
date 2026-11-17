CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(64) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

INSERT INTO usuario(login, senha) VALUES 
('admin', 'admin'),
('vendas', 'vendas'),
('estoque', 'estoque');

SELECT * FROM usuario;