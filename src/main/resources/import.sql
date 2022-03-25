INSERT INTO cozinha (id, nome) values (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) values (2, 'Indiana');

INSERT INTO restaurante (nome, taxa_frete, cozinha_id) values ('Tayland Day', 10.00, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) values ('Gran fineza', 18.00, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Indian', 07.00, 2);

INSERT INTO forma_pagamento(descricao) values ('CARTAO CREDITO');
INSERT INTO forma_pagamento(descricao) values ('CARTAO DEBITO');
INSERT INTO forma_pagamento(descricao) values ('DINHEIRO');

INSERT INTO permissao(nome, descricao) values ('admin', 'acesso total a consultas, inserções e deleções de informações');
INSERT INTO permissao(nome, descricao) values ('user', 'acesso restrito apenas à consultas');

INSERT INTO estado(nome, sigla) values ('Minas Gerais', 'MG'); 
INSERT INTO estado(nome, sigla) values ('São Paulo', 'SP');

INSERT INTO cidade(nome, estado_id) values ('Belo Horizonte', 1);
INSERT INTO cidade(nome, estado_id) values ('Ipatinga', 1);
INSERT INTO cidade(nome, estado_id) values ('São Paulo', 2);
INSERT INTO cidade(nome, estado_id) values ('Guarulhos', 2);
 