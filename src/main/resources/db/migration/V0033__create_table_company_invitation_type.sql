CREATE TABLE `company_invitation_type`
(
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `external_id` char(36) UNIQUE NOT NULL,
    `company_id` int NOT NULL,
    `invitation_type_id` int NOT NULL,
    `created_at` datetime NOT NULL DEFAULT (now()),
    `updated_at` datetime
);

ALTER TABLE `company_invitation_type`
    ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

ALTER TABLE `company_invitation_type`
    ADD FOREIGN KEY (`invitation_type_id`) REFERENCES `invitation_type` (`id`);

CREATE UNIQUE INDEX U_IDX_COMPANY_INVITATION_TYPE ON company_invitation_type (company_id, invitation_type_id);