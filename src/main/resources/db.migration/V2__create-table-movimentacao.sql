CREATE TABLE movimentacao (
    id bigint not null auto_increment primary key,
    tipo varchar(255) not null,
    quantidade int not null,
    data_movimentacao datetime not null,

    CONSTRAINT fk_produto_id FOREIGN KEY (produto_id) REFERENCES produtos (id)
);