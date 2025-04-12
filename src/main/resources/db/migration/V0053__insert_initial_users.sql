insert into user (external_id, name, email, username, password, company_id, active, update_password_next_login, admin)
    values (uuid(), 'Cristian Dall''Oca Berti', 'cristiandalloca@gmail.com',
            'cristian.berti', '$2a$10$kh1DGfVtH/Nlcq.zw14OW.paKoyGBmVm6lssVTVlL0bamNjsVsjtq',
            (SELECT c.id FROM company c where c.name = 'BDK'), true, false, true);

insert into user (external_id, name, email, username, password, company_id, active, update_password_next_login, admin)
values (uuid(), 'Fernanda Kimie Kishimoto', 'fernandakimiek@gmail.com',
        'fernanda.kishimoto', '$2a$10$kh1DGfVtH/Nlcq.zw14OW.paKoyGBmVm6lssVTVlL0bamNjsVsjtq',
        (SELECT c.id FROM company c where c.name = 'BDK'), true, false, true);