delete from profile_privilege where privilege_id = (select id from privilege where name =  'CADASTRO_ESTADO.VISUALIZAR');
delete from privilege where name = 'CADASTRO_ESTADO.VISUALIZAR';