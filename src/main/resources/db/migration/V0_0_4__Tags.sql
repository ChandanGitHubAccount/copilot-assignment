CREATE TABLE IF NOT EXISTS `tags` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(250) NOT NULL,
    `article` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `tags_fk_article` FOREIGN KEY (`article`) REFERENCES `article` (`id`)
);