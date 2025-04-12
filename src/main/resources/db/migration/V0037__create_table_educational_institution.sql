CREATE TABLE `educational_institution` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `external_id` char(36) UNIQUE NOT NULL,
   `name` varchar(255) NOT NULL,
   `type` VARCHAR(20) NOT NULL,
   `city_id` INT NOT NULL,
   `active` bool not null default true,
   `created_at` datetime NOT NULL DEFAULT (now()),
   `updated_at` datetime
);

ALTER TABLE `educational_institution`
    ADD FOREIGN KEY (`city_id`) REFERENCES `city` (`id`);

CREATE UNIQUE INDEX U_IDX_EDUCATIONAL_INSTITUTION_NAME ON educational_institution (name);