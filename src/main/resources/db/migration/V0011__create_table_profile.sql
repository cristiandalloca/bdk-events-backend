CREATE TABLE `profile` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `external_id` char(36) UNIQUE NOT NULL,
   `name` varchar(255) NOT NULL,
   `company_id` INT NOT NULL,
   `created_at` datetime NOT NULL DEFAULT (now()),
   `updated_at` datetime
);

ALTER TABLE `profile`
    ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

CREATE UNIQUE INDEX U_IDX_PROFILE_NAME_COUNTRY ON profile (name, company_id);