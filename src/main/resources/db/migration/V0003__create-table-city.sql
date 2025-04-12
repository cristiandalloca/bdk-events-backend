CREATE TABLE `city`
(
    `id`          int PRIMARY KEY AUTO_INCREMENT,
    `external_id` varchar(36) UNIQUE NOT NULL,
    `name`        varchar(255)       NOT NULL,
    `state_id`    int                NOT NULL,
    `created_at`  datetime           NOT NULL DEFAULT (now()),
    `updated_at`  datetime
);

ALTER TABLE `city`
    ADD FOREIGN KEY (`state_id`) REFERENCES `state` (`id`);