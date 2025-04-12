CREATE TABLE service_supplier (
    id int PRIMARY KEY AUTO_INCREMENT,
    external_id char(36) UNIQUE NOT NULL,
    service_id int NOT NULL,
    supplier_id int NOT NULL,
    created_at datetime NOT NULL DEFAULT (now()),
    updated_at datetime
);

ALTER TABLE service_supplier
    ADD FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE service_supplier
    ADD FOREIGN KEY (service_id) REFERENCES service (id);
