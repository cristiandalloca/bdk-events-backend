insert into city (external_id, name, state_id) values (uuid(), 'Florianópolis', (select id from state where acronym = 'SC'));

insert into company (external_id, name, business_name, document, email, zip_code, street, street_number, district, complement, city_id)
values (UUID(), 'BDK', 'BDK Gestão Eventos', '44361334862', 'bdkgestaoeventos@gmail.com', '88058320', 'Tv Nildo Neponoceno Fernandes', '397', 'Ingleses do Rio Vermelho', 'Apto 204 B', (select id from city where name = 'Florianópolis'));
