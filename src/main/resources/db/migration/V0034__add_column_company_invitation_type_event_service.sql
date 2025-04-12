ALTER TABLE `event_service`
    ADD COLUMN `company_invitation_type_id` INT NULL AFTER `measurement_type`;

ALTER TABLE `event_service`
    ADD FOREIGN KEY (`company_invitation_type_id`) REFERENCES `company_invitation_type` (`id`);