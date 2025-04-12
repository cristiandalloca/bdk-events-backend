ALTER TABLE `user`
    ADD COLUMN `admin` BOOLEAN DEFAULT 0 NULL AFTER `update_password_next_login`;

UPDATE user SET user.admin = false;

ALTER TABLE user
    MODIFY COLUMN admin BOOLEAN DEFAULT 0 NOT NULL;