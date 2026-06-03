CREATE TABLE produtos(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    descricao varchar(100) not null,
    preco float not null,
    quantidadeEstoque int not null,

    primary key(id)
);