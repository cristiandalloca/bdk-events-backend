CREATE TABLE `privilege` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `external_id` char(36) UNIQUE NOT NULL,
   `name` varchar(50) NOT NULL,
   `description` varchar(255) NOT NULL,
   `created_at` datetime NOT NULL DEFAULT (now()),
   `updated_at` datetime
);

CREATE UNIQUE INDEX U_IDX_PRIVILEGE_NAME_PARENT_PRIVILEGE ON privilege (name);
CREATE UNIQUE INDEX U_IDX_PRIVILEGE_IDENTIFIER ON privilege (description);