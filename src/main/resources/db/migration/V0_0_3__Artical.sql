CREATE TABLE IF NOT EXISTS article (
    `id` INT AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `sub_title` VARCHAR(255) DEFAULT NULL,
    `author` INT NOT NULL,
    `category` INT NOT NULL,
    `image` VARCHAR(512) DEFAULT NULL,
    `type` ENUM('TEXT', 'AUDIO', 'VIDEO') NOT NULL,
    `media_url` VARCHAR(512) DEFAULT NULL,
    `publish_date` datetime NOT NULL,
    `description` LONGTEXT DEFAULT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT `article_fk_category` FOREIGN KEY(category) REFERENCES category(id),
    CONSTRAINT `article_fk_author` FOREIGN KEY(author) REFERENCES author(id)
);