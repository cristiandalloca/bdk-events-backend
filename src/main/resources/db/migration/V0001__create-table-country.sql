CREATE TABLE `country` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `external_id` varchar(36) UNIQUE NOT NULL,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(3) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT (now()),
  `updated_at` datetime
);