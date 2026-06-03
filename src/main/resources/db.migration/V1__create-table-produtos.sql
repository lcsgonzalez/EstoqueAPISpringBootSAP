CREATE TABLE produtos(
    id bigint not null auto_increment primary key,
    nome varchar(255) not null,
    descricao varchar(255) not null,
    preco decimal not null,
    quantidadeEstoque int not null
);