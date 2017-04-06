INSERT INTO CEP (STREET, DISTRICT, CITY, STATE, CEP) VALUES ('Av. Paulista', 'Paraíso', 'São Paulo', 'SP', '01311000'); 
INSERT INTO CEP (STREET, DISTRICT, CITY, STATE, CEP) VALUES ('Av. Cândido de Abreu', 'Centro Cívico', 'Curitiba','PR', '80530908'); 
INSERT INTO CEP (STREET, DISTRICT, CITY, STATE, CEP) VALUES ('Av. Pres. Castelo Branco', 'Maracanã', 'Rio de Janeiro', 'RJ', '20271130');

INSERT INTO ADDRESS (CEP_ID, NUMBER, COMPLEMENT) VALUES (1, 287, 'conjunto 161');
INSERT INTO ADDRESS (CEP_ID, NUMBER, COMPLEMENT) VALUES (1, 604, null);
INSERT INTO ADDRESS (CEP_ID, NUMBER, COMPLEMENT)  VALUES (2, 513, 'cj. 65');
INSERT INTO ADDRESS (CEP_ID, NUMBER, COMPLEMENT)  VALUES (3, 1000, null);