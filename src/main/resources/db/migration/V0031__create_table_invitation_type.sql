CREATE TABLE `invitation_type`
(
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `external_id` char(36) UNIQUE NOT NULL,
    `name` varchar(50) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT (now()),
    `updated_at` datetime
);

CREATE UNIQUE INDEX U_IDX_INVITATION_TYPE_NAME ON invitation_type (name);