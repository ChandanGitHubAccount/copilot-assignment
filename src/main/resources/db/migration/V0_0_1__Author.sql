CREATE TABLE IF NOT EXISTS `author` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(250) NOT NULL,
    `image` VARCHAR(200) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `deleted` boolean DEFAULT false,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);