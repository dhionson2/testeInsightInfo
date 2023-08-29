/* SCRIPT CRIAÇÃO usuario */
CREATE TABLE usuario
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    ativo BOOLEAN,
    cpf VARCHAR(30),
    data_nascimento DATE,
    email VARCHAR(50),
    login VARCHAR(50),
    nome VARCHAR(255),
    password VARCHAR(50),
    PRIMARY KEY (id)
);
/* .SCRIPT CRIAÇÃO usuario */

/* SCRIPT CRIAÇÃO horario_trabalho */
CREATE TABLE horario_trabalho (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    entrada TIME NOT NULL,
    saida TIME NOT NULL
);

/* .SCRIPT CRIAÇÃO horario_trabalho */

/* SCRIPT CRIAÇÃO MarcacoesFeitas */
CREATE TABLE MarcacoesFeitas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    entrada TIME NOT NULL,
    saida TIME NOT NULL,
    data DATE NOT NULL,
    atraso TIME
);


/* SCRIPT CRIAÇÃO MarcacoesFeitas */