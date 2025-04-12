CREATE TABLE `event_service` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `external_id` char(36) UNIQUE NOT NULL,
   `event_id` int NOT NULL,
   `service_id` int NOT NULL,
   `price` decimal (9,2),
   `display_in_proposal` bool NOT NULL DEFAULT FALSE,
   `display_price_in_proposal` bool NOT NULL DEFAULT FALSE,
   `allow_change_quantity` bool NOT NULL DEFAULT FALSE,
   `allow_change_price` bool NOT NULL DEFAULT FALSE,
   `measurement_type` varchar(120) NOT NULL,
   `created_at` datetime NOT NULL DEFAULT (now()),
   `updated_at` datetime
);

ALTER TABLE `event_service`
    ADD FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

ALTER TABLE `event_service`
    ADD FOREIGN KEY (`service_id`) REFERENCES `service` (`id`);

CREATE UNIQUE INDEX U_IDX_EVENT_SERVICE ON event_service (event_id, service_id);