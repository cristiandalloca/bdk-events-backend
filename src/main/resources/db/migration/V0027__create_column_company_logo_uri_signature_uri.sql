ALTER TABLE `company_parameter`
ADD COLUMN `logo_uri` VARCHAR(255) NULL AFTER `company_id`,
ADD COLUMN `signature_uri` VARCHAR(255) NULL AFTER `logo_uri`;