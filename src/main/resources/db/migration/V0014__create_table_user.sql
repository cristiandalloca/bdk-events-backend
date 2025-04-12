CREATE TABLE `user` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `external_id` char(36) UNIQUE NOT NULL,
   `name` varchar(255) NOT NULL,
   `email` varchar(124) NOT NULL,
   `username` varchar(50) NOT NULL,
   `password` varchar(255) NOT NULL,
   `company_id` int NOT NULL,
   `active` bool not null default true,
   `created_at` datetime NOT NULL DEFAULT (now()),
   `updated_at` datetime
);

ALTER TABLE `user`
    ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

CREATE UNIQUE INDEX U_IDX_USER_USERNAME ON user (username);