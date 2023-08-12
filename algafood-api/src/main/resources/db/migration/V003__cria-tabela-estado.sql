CREATE TABLE Estado (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(40) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO Estado (nome) SELECT DISTINCT nome_estado FROM Cidade;

ALTER TABLE Cidade ADD COLUMN estado_id BIGINT NOT NULL;

UPDATE Cidade c SET c.estado_id = (SELECT e.id FROM Estado e WHERE e.nome = c.nome_estado);

ALTER TABLE Cidade ADD CONSTRAINT fk_cidade_estado FOREIGN KEY (estado_id) REFERENCES Estado (id) ON DELETE CASCADE;

ALTER TABLE Cidade DROP COLUMN nome_estado;
ALTER TABLE Cidade CHANGE nome_cidade nome VARCHAR(80) NOT NULL;