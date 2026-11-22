CREATE TABLE fornecedor (
    id_fornecedor BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    razao_social VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150),

    cnpj VARCHAR(14) UNIQUE CHECK (cnpj ~ '^[0-9]{18}$'),

    telefone VARCHAR(11) CHECK (telefone ~ '^[0-9]{10,11}$'),
    email VARCHAR(150),

    endereco VARCHAR(200),
    cidade VARCHAR(100),
    estado CHAR(2),

    ativo BOOLEAN NOT NULL DEFAULT TRUE
);