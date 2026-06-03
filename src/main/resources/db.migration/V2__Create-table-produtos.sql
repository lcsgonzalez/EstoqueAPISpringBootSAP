CREATE TABLE produto (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         quantidade INT NOT NULL DEFAULT 0,
                         limite_minimo INT NOT NULL
);