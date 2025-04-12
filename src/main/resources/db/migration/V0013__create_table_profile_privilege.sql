CREATE TABLE `profile_privilege` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `profile_id` INT NOT NULL,
   `privilege_id` INT NOT NULL
);

ALTER TABLE `profile_privilege`
    ADD FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`);

ALTER TABLE `profile_privilege`
    ADD FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`);

CREATE UNIQUE INDEX U_IDX_PROFILE_PRIVILEGE ON profile_privilege (profile_id, privilege_id);