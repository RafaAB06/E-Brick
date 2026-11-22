CREATE TABLE cliente (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,

    telefone VARCHAR(11) CHECK (telefone ~ '^[0-9]{10,11}$'),
    email VARCHAR(150),

    endereco VARCHAR(200),
    cidade VARCHAR(100),
    estado CHAR(2),

    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO cliente(nome, telefone, email, endereco, cidade, estado) VALUES
('Richarlison Nunes Morais', '49989189271', 'rich@gmail.com', 'Rua Dom Pedro Primeiro 25', 'Guaraciaba', 'SC'),
('Jesuína Diocléstia', '4998283765', 'evilmode203@gmail.com', 'Rua Duque de Caxias 65', 'Guaraciaba', 'SC'),
('Berpal Materiais de Construção LTDA', '36451234', 'berpal@gmail.com','Rua Ana Riffel 364', 'Princesa', 'SC');

SELECT * FROM cliente;

--###########################################################################################################--

CREATE TABLE pessoa_fisica (
    cpf VARCHAR(11) PRIMARY KEY CHECK (cpf ~ '^[0-9]{11}$'),
    cliente_id BIGINT UNIQUE REFERENCES cliente(id)
);

INSERT INTO pessoa_fisica(cpf, cliente_id) VALUES
('12345678910', 1),
('99966699969', 2);

SELECT * FROM pessoa_fisica;

--###########################################################################################################--

CREATE TABLE pessoa_juridica(
	cnpj VARCHAR(14) PRIMARY KEY CHECK (cnpj ~ '^[0-9]{18}$'),
    cliente_id BIGINT UNIQUE REFERENCES cliente(id)
);

INSERT INTO pessoa_juridica(cnpj, cliente_id) VALUES 
('12345678000123', 3);

SELECT * FROM pessoa_juridica;

--###################################################################################################################

DROP TYPE cliente_completo;
CREATE TYPE cliente_completo AS (
    id BIGINT,
    nome VARCHAR(150),
    telefone VARCHAR(11),
    email VARCHAR(150),
    endereco VARCHAR(200),
    cidade VARCHAR(100),
    estado CHAR(2),
    ativo BOOLEAN,
    cpf VARCHAR(11),
    cnpj VARCHAR(14)
);

DROP FUNCTION buscar_clientes(VARCHAR, VARCHAR, int4, int4);
CREATE OR REPLACE FUNCTION buscar_clientes(pesquisa VARCHAR, ordem VARCHAR, inicio int4, qtd int4)
RETURNS SETOF cliente_completo
AS $$
BEGIN
    RETURN QUERY
    SELECT c.id, c.nome, c.telefone, c.email, c.endereco, c.cidade, c.estado, c.ativo, pf.cpf, pj.cnpj
    FROM cliente AS c
    LEFT JOIN pessoa_fisica AS pf ON c.id = pf.cliente_id
    LEFT JOIN pessoa_juridica AS pj ON c.id = pj.cliente_id
    
    WHERE
    (pesquisa IS NULL OR TRIM(pesquisa) = ''
        OR c.nome ILIKE '%' || pesquisa || '%'
        OR c.cidade ILIKE '%' || pesquisa || '%'
        OR c.estado ILIKE '%' || pesquisa || '%'
        OR pf.cpf LIKE '%' || pesquisa || '%'
        OR pj.cnpj LIKE '%' || pesquisa || '%')
    
    ORDER BY
	    CASE
	        WHEN ordem = 'cpf' THEN pf.cpf
	        WHEN ordem = 'cnpj' THEN pj.cnpj
	        WHEN ordem = 'cidade' then c.cidade
	        WHEN ordem = 'estado' then c.estado
	        ELSE c.nome
	    END,
	    CASE
	        WHEN ordem = 'ativo' THEN c.ativo
	    END
	    LIMIT qtd OFFSET inicio;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM buscar_clientes('', 'cidade', 0, 5);

CREATE OR REPLACE PROCEDURE atualizar_cliente(c cliente_completo)
AS $$
BEGIN
    UPDATE cliente
    SET 
    	nome = c.nome,
    	telefone = c.telefone,
    	email = c.email,
    	endereco = c.endereco,
    	cidade = c.cidade,
    	estado = c.estado,
    	ativo = c.ativo
    WHERE id = c.id;
    
	IF c.cpf IS NOT NULL THEN
		DELETE FROM pessoa_juridica AS pj WHERE pj.cliente_id = c.id;
		UPDATE pessoa_fisica AS pf
		SET
			cpf = c.cpf
		WHERE pf.cliente_id = c.id;
	ELSIF c.cnpj IS NOT NULL THEN 
		DELETE FROM pessoa_fisica AS pf WHERE pf.cliente_id = c.id;
		UPDATE pessoa_juridica AS pj
		SET 
			cnpj = c.cnpj
		WHERE pj.cliente_id = c.id;
	END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE adicionar_cliente(
	c_nome VARCHAR,
	c_telefone VARCHAR,
	c_email VARCHAR,
	c_endereco VARCHAR,
	c_cidade VARCHAR,
	c_estado CHAR(2),
	c_cpf CHAR(11),
	c_cnpj VARCHAR(18)
)
AS $$
	DECLARE c_id BIGINT;
BEGIN
   	INSERT INTO cliente(nome, telefone, email, endereco, cidade, estado)
	VALUES (c_nome, c_telefone, c_email, c_endereco, c_cidade, c_estado)
	RETURNING id INTO c_id;
	
	IF c_cpf IS NOT NULL THEN
		INSERT INTO pessoa_fisica(cpf, cliente_id)
		VALUES(c_cpf, c_id);
	ELSIF c_cnpj IS NOT NULL THEN 
		INSERT INTO pessoa_juridica(cpf, cliente_id)
		VALUES(c_cnpj, c_id);
	END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE excluir_cliente(c_id BIGINT)
AS $$
BEGIN
	DELETE FROM pessoa_fisica AS pf WHERE pf.cliente_id = c_id;
   	DELETE FROM pessoa_juridica AS pj WHERE pj.cliente_id = c_id;
   	DELETE FROM cliente AS c WHERE c.id = c_id;
END;
$$ LANGUAGE plpgsql;
