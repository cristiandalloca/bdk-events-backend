CREATE TABLE `event` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `external_id` char(36) UNIQUE NOT NULL,
   `name` varchar(255) NOT NULL,
   `company_id` INT NOT NULL,
   `active` bool not null default true,
   `created_at` datetime NOT NULL DEFAULT (now()),
   `updated_at` datetime
);

ALTER TABLE `event`
    ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

CREATE UNIQUE INDEX U_IDX_EVENT_NAME_COUNTRY ON event (name, company_id);