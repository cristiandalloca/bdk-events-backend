CREATE TABLE `event_type`
(
    `event_id` int NOT NULL,
    `type` varchar(255) NOT NULL
);

ALTER TABLE `event_type`
    ADD FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);