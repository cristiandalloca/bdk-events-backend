CREATE TABLE `user_profile` (
   `id` int PRIMARY KEY AUTO_INCREMENT,
   `user_id` int NOT NULL,
   `profile_id` int NOT NULL
);

ALTER TABLE `user_profile`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `user_profile`
    ADD FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`);

CREATE UNIQUE INDEX U_IDX_USER_PROFILE ON user_profile (user_id, profile_id);