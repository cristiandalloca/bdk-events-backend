CREATE TABLE `company_parameter`
(
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `company_id` int NOT NULL,
    `logo` LONGBLOB,
    `signature` LONGBLOB,
    `initial_paragraph_contract` LONGTEXT,
    `created_at` datetime NOT NULL DEFAULT (now()),
    `updated_at` datetime
);

ALTER TABLE `company_parameter`
    ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);