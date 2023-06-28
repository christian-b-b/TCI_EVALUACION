INSERT INTO T_USER(name,state,registrationDate) VALUES('CHRISTIAN',1,NOW());
INSERT INTO T_USER_PASSWORD(idUser,password,state,registrationDate) VALUES(1,'BALDEON',1,NOW());
INSERT INTO T_ROLE(code,name,state,registrationDate) VALUES ('USER','USER',1,NOW());
INSERT INTO T_USER_ROLE(idUser,idRole,state,registrationDate) VALUES (1,1,1,NOW());
