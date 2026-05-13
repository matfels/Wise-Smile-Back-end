drop database WiseStar;
create database WiseStar;
use WiseStar;
drop table if exists pacientes;

create table usuarios ( 
id int auto_increment primary key,
nome varchar(100) not null,
cpf varchar(11) not null,
email varchar(100) unique,
senha varchar(100) not null,
data_criacao timestamp default current_timestamp,
ultimo_login timestamp,
perfil varchar(100),
ativo boolean
);

create table pacientes (
	id INT auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) unique,
    cpf varchar(11),
    telefone int(11),
    data_criacao timestamp default current_timestamp
);

create table dentista (
	id int auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) unique,
    cpf varchar(11),
    cro varchar(6),
    data_criacao timestamp default current_timestamp,
    ativo boolean not null default true
);

create table especialidades( 
	id int auto_increment primary key,
	nome varchar(100)
);

create table dentista_especialidade(
	id int auto_increment primary key,
    FOREIGN KEY (id_dentista) REFERENCES dentista(id),
    foreign key (id_especialidade) references especialidades(id)
);
    
create table consultas( 
	id int auto_increment primary key, 
    foreign key (id_paciente) references pacientes(id),
    foreign key (id_dentista) references dentista(id),
    foreign key (id_usuario) references usuaruios(id),
    descricao varchar(100) not null,
    motivo_cancelamento varchar (100),
    data_inicio timestamp not null,
    data_fim timestamp not null,
    data_registro timestamp,
    status enum('AGENDADA','CANCELADA','FINALIZADA') not null default 'AGENDADA'
);