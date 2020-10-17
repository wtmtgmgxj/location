create database if not exists location;
use location;

CREATE TABLE `location` (
  `id`         BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(50)           DEFAULT NULL,
  `created_by` VARCHAR(50)           NOT NULL,
  `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE (`name`),
  KEY idx_updated_on (updated_on)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;