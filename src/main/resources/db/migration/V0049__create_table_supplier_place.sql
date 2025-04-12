CREATE TABLE supplier_place (
    id int PRIMARY KEY AUTO_INCREMENT,
    external_id char(36) UNIQUE NOT NULL,
    name varchar(255) NOT NULL,
    description LONGTEXT,
    maximum_capacity_people int NOT NULL,
    supplier_id int NOT NULL,
    active bool not null default true,
    created_at datetime NOT NULL DEFAULT (now()),
    updated_at datetime
);

ALTER TABLE supplier_place
    ADD FOREIGN KEY (supplier_id) REFERENCES `supplier` (`id`);