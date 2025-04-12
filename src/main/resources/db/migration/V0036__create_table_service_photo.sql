CREATE TABLE `service_photo` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `service_id` int NOT NULL,
    `external_id` char(36) UNIQUE NOT NULL,
    `number` int NOT NULL,
    `uri` VARCHAR(255) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT (now()),
    `updated_at` datetime
);


ALTER TABLE `service_photo`
    ADD FOREIGN KEY (`service_id`) REFERENCES `service` (`id`);

CREATE UNIQUE INDEX U_IDX_SERVICE_PHOTO_NUMBER ON service_photo (service_id, number);