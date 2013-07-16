create table votacao (
  id int not null,
  nomeEsquerda varchar(50) not null,
  nomeDireita varchar(50) not null,
  inicio timestamp,
  fim timestamp,
  
  primary key (id)
);

create table votos (
  idVotacao int not null,
  votosEsquerda int,
  votosDireita int,
  horaRecebimento timestamp default current_timestamp,
  
  foreign key (idVotacao) references votacao(id)
);

