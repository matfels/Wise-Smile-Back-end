drop database sistema_gestao_consultas;
create database sistema_gestao_consultas;
use sistema_gestao_consultas;
drop table if exists sistema_gestao_consultas;

create table usuarios ( 
id integer auto_increment primary key,
nome varchar(100) not null,
cpf varchar(14) unique not null,
email varchar(100) unique not null,
senha varchar(100) not null,
perfil enum('AGENDADA','CANCELADA','FINALIZADA') not null default 'AGENDADA' not null,

data_criacao timestamp default current_timestamp,
ultimo_login timestamp

);

create table pacientes (
	id INTEGER auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) unique not null,
    cpf varchar(11) unique not null,
    telefone varchar(15),
    ativo boolean default true,
    data_criacao timestamp default current_timestamp
);

create table dentista (
	id integer auto_increment primary key,
    nome varchar(100) not null,
    cpf varchar(11) unique not null,
    email varchar(100) unique not null,
    cro varchar(6) not null,
    ativo boolean default true,
    data_criacao timestamp default current_timestamp
);

create table especialidades( 
	id integer auto_increment primary key,
	nome varchar(100) not null
);

create table dentista_especialidade(
	id integer auto_increment primary key,
    id_dentista integer not null,
    id_especialidade integer not null,
    FOREIGN KEY (id_dentista) REFERENCES dentista(id),
    foreign key (id_especialidade) references especialidades(id)
);
    
create table consultas( 
	id integer auto_increment primary key, 
    id_paciente integer not null,
    id_dentista integer not null,
    id_usuario integer not null,
    foreign key (id_paciente) references pacientes(id),
    foreign key (id_dentista) references dentista(id),
    foreign key (id_usuario) references usuarios(id),
    descricao text not null,
    motivo_cancelamento text,
    data_inicio datetime not null,
    data_fim datetime not null,
    data_registro timestamp default current_timestamp,
    status enum('AGENDADA','CANCELADA','FINALIZADA') not null default 'AGENDADA'
);


SHOW VARIABLES LIKE 'datadir';