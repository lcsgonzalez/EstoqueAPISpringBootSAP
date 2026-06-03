ALTER TABLE movimentacao
    ADD COLUMN data_movimentacao DATETIME,
ADD COLUMN usuario_id BIGINT,
ADD CONSTRAINT fk_movimentacao_usuario
FOREIGN KEY (usuario_id)
REFERENCES usuarios(id);