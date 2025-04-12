CREATE TABLE supplier (
    id int PRIMARY KEY AUTO_INCREMENT,
    external_id char(36) UNIQUE NOT NULL,
    name varchar(255) NOT NULL,
    phone_number varchar(11),
    cell_phone_number varchar(11),
    email varchar(124),
    postal_code char(8) NOT NULL,
    street char(255) NOT NULL,
    street_number varchar(12),
    neighborhood varchar(255) NOT NULL,
    complement varchar(50),
    city_id int NOT NULL,
    company_id int NOT NULL,
    active bool not null default true,
    has_events_places bool not null default false,
    created_at datetime NOT NULL DEFAULT (now()),
    updated_at datetime
);

ALTER TABLE supplier
    ADD FOREIGN KEY (city_id) REFERENCES city (id);
ALTER TABLE supplier
    ADD FOREIGN KEY (company_id) REFERENCES company (id);