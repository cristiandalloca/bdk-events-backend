CREATE TABLE `company`
(
    `id`          int PRIMARY KEY AUTO_INCREMENT,
    `external_id` varchar(36) UNIQUE NOT NULL,
    `name`        varchar(255)       NOT NULL,
    `document`          varchar(14)        NOT NULL,
    `state_registration` varchar(14) NOT NULL,
    `business_name` varchar(255)     NOT NULL,
    `city_id`       int                NOT NULL,
    `active`    bool not null default true,
    `created_at`  datetime           NOT NULL DEFAULT (now()),
    `updated_at`  datetime
);

ALTER TABLE `company`
    ADD FOREIGN KEY (`city_id`) REFERENCES `city` (`id`);