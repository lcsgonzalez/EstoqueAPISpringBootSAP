CREATE TABLE movimentacao (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              tipo VARCHAR(20) NOT NULL,
                              quantidade INT NOT NULL,
                              data_movimentacao DATETIME NOT NULL,
                              produto_id BIGINT NOT NULL,

                              CONSTRAINT fk_movimentacao_produto
                                  FOREIGN KEY (produto_id)
                                      REFERENCES produto(id)
                                      ON DELETE CASCADE
);