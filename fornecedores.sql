create type fornecedor_type_enum as enum ('VENDAS', 'SERVICOS');
drop type fornecedor_type_enum;
create type status_type_enum as enum ('ATIVO', 'INATIVO');
create type empresa_type_enum as enum ('FILIAL', 'MATRIZ');

create table telefones (
	id serial primary key,
	ddd varchar,
	numero varchar,
	tipo varchar,
	created_at date
);

insert into telefones (ddd, numero, tipo) values ('11', '981297480', 'celular');
select * from telefones;

create table contatos (
	id serial primary key,
	tel_id int,
	email varchar,
	departamento varchar,
	created_at date,
	constraint fk_telefone foreign key (tel_id) references telefones(id)
);

alter table contatos add column nome varchar, add column idade int;

insert into contatos (tel_id, email, departamento) values (2, 'mano@hub.com.br', 'Fintech');
select * from contatos join telefones on contatos.tel_id = telefones.id;

create table fornecedores (
	id serial primary key,
	tel_id int,
	con_id int,
	email varchar,
	cnpj varchar,
	inscricao_municipal varchar,
	inscricao_estadual varchar,
	razao_social varchar,
	nome_fantasia varchar,
	tipo_fornecedor fornecedor_type_enum,
	status status_type_enum,
	created_at date,
	constraint fk_telefone_fornecedor foreign key (tel_id) references telefones(id),
	constraint fk_contato_fornecedor foreign key (con_id) references contatos(id)
);

ALTER TABLE fornecedores ADD COLUMN end_id int, ADD CONSTRAINT fk_endereco_fornecedor FOREIGN KEY (end_id) REFERENCES enderecos(id);

drop table fornecedores;

insert into fornecedores (
	tel_id,
	con_id,
	email,
	cnpj,
	inscricao_municipal,
	inscricao_estadual,
	razao_social,
	nome_fantasia,
	tipo_fornecedor,
	status
) values (
	1,
	2,
	'atka@hub.com',
	'12345678901234',
	'123456',
	'123456',
	'Atka tech',
	'Atka',
	'SERVICO',
	'ATIVO'
);

select * from fornecedores join telefones on fornecedores.tel_id = telefones.id join contatos on fornecedores.con_id = contatos.id;

create table cnaes (
	id serial primary key,
	numero varchar,
	for_id int,
	created_at date,
	constraint fk_fornecedor_cnae foreign key (for_id) references fornecedores(id)
) ;

insert into cnaes (numero, for_id) values (15654, 2);
drop table cnaes;
select * from cnaes join fornecedores on cnaes.for_id = fornecedores.id;

create table produtos (
	id serial primary key,
	nome varchar,
	for_id int,
	descricao varchar,
	created_at date,
	constraint fk_produto_fornecedor foreign key (for_id) references fornecedores(id)
);

insert into produtos (nome, for_id, descricao) values ('Lapis', 1, 'Lapis comum');

select * from produtos ;
select * from produtos join fornecedores on produtos.for_id = fornecedores.id;

create table empresas (
	id serial primary key,
	for_id int,
	tipo_empresa empresa_type_enum,
	created_at date,
	constraint fk_empresa_fornecedor foreign key (for_id) references fornecedores(id)
);

insert into empresas (for_id, tipo_empresa) values (1, 'FILIAL');

select * from empresas;

CREATE TABLE enderecos (
	id serial PRIMARY KEY,
	cep varchar,
	logradouro varchar,
	numero varchar,
	bairro varchar,
	complemento varchar,
	cidade varchar,
	estado varchar,
	pais varchar,
	created_at date
);







