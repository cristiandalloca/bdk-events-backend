delete from profile_privilege where privilege_id = (select id from privilege where name =  'CADASTRO_EMPRESA.INSERIR');
delete from profile_privilege where privilege_id = (select id from privilege where name =  'CADASTRO_EMPRESA.REMOVER');
delete from privilege where name = 'CADASTRO_EMPRESA.INSERIR';
delete from privilege where name = 'CADASTRO_EMPRESA.REMOVER';