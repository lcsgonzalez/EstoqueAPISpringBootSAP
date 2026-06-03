create table produtos(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    descricao varchar(200),
    preco decimal not null,
    quantidade_estoque integer not null,

    primary key (id)
)