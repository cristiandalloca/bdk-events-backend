CREATE TABLE `request_log` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `url` varchar(255),
    `method` varchar(10),
    `ip` varchar(128),
    `agent` varchar(255),
    `query_param` varchar(255),
    `time_ms` long,
    `response_code` int,
    `created_at` datetime NOT NULL DEFAULT (now())
);

CREATE TABLE `request_log_detail` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `id_request_log` int NOT NULL,
    `request_header` LONGTEXT,
    `request_body` LONGTEXT,
    `response_body` LONGTEXT,
    `response_header` LONGTEXT
);

ALTER TABLE `request_log_detail`
    ADD FOREIGN KEY (`id_request_log`) REFERENCES `request_log` (`id`);
