insert into Institution (cnpj, name, pixeon) values ('99111000999900', 'Dum Dum Clinic', 2);
insert into Institution (cnpj, name, pixeon) values ('88111000777700', 'Pobre Clinic', 0);
insert into User (email, password, role, cnpj) values ('admin@admin', 'admin', 'ADMIN', '99111000999900');
insert into User (email, password, role, cnpj) values ('other@other', 'other', 'CLIENT_ADMIN', '88111000777700');