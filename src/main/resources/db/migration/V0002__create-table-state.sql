CREATE TABLE `state`
(
    `id`          int PRIMARY KEY AUTO_INCREMENT,
    `external_id` varchar(36) UNIQUE NOT NULL,
    `name`        varchar(255)       NOT NULL,
    `acronym`     varchar(2)         NOT NULL,
    `country_id`  int                NOT NULL,
    `created_at` datetime NOT NULL DEFAULT (now()),
    `updated_at` datetime
);

ALTER TABLE `state` ADD FOREIGN KEY (`country_id`) REFERENCES `country` (`id`);