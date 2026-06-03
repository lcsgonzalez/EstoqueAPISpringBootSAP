create table movimentacao(
    id bigint not null auto_increment,
    tipo varchar(7) not null,
    quantidade int not null,
    data_movimentacao datetime not null,
    produto_id bigint not null,
    usuario_id bigint not null,
    primary key (id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
)