CREATE TABLE `service` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `external_id` char(36) UNIQUE NOT NULL,
   `name` varchar(255) NOT NULL,
   `description` LONGTEXT,
   `cost_type` varchar(50) NOT NULL,
   `minimum_price` decimal(9,2) NOT NULL,
   `apply_bdi` bool NOT NULL DEFAULT FALSE,
   `company_id` INT NOT NULL,
   `active` bool NOT NULL DEFAULT TRUE,
   `created_at` datetime NOT NULL DEFAULT (now()),
   `updated_at` datetime
);

ALTER TABLE `service`
    ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

CREATE UNIQUE INDEX U_IDX_SERVICE_NAME_COUNTRY ON service (name, company_id);