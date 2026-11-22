CREATE TABLE usuario_permissao (
    usuario_id INTEGER REFERENCES usuario(id),
    permissao_id VARCHAR(64) REFERENCES permissao(id),

    PRIMARY KEY (usuario_id, permissao_id)
);

INSERT INTO usuario_permissao(usuario_id, permissao_id) VALUES
(1, 'vendas'),
(1, 'fornecedores'),
(1, 'clientes'),
(1, 'estoque'),
(1, 'usuarios');

CREATE OR REPLACE FUNCTION obter_permissoes(p_login VARCHAR)
RETURNS TABLE(id VARCHAR, nome VARCHAR)
AS $$
BEGIN
    RETURN QUERY
    SELECT p.id, p.nome
    FROM permissao AS p
    JOIN usuario_permissao AS up ON up.permissao_id = p.id
    JOIN usuario AS u ON up.usuario_id = u.id
    WHERE u.login = p_login;
END;
$$ LANGUAGE plpgsql;
