DROP TABLE IF EXISTS `company`;

CREATE TABLE `company`
(
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `external_id` varchar(36) UNIQUE NOT NULL,
    `name` varchar(255) NOT NULL,
    `business_name` varchar(255) NOT NULL,
    `document` char(14) NOT NULL,
    `state_registration_number` char(14),
    `city_registration_number` char(11),
    `phone_number` varchar(11),
    `email` varchar(124),
    `zip_code` char(8) NOT NULL,
    `street` char(255) NOT NULL,
    `street_number` varchar(12),
    `district` varchar(255) NOT NULL,
    `complement` varchar(50),
    `city_id` int NOT NULL,
    `active` bool not null default true,
    `created_at` datetime NOT NULL DEFAULT (now()),
    `updated_at` datetime
);

ALTER TABLE `company`
    ADD FOREIGN KEY (`city_id`) REFERENCES `city` (`id`);