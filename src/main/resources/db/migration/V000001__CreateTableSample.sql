create database if not exists location;
use location;

CREATE TABLE `location` (
  `id`         BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(50)           DEFAULT NULL,
  `created_by` VARCHAR(50)           NOT NULL,
  `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `requests` json DEFAULT NULL,
  `tags` json DEFAULT NULL,
  `children` json DEFAULT NULL,
  `status` VARCHAR(20) DEFAULT "ACTIVE" NOT NULL,
  `report_count` bigint DEFAULT 0,
  `geo_location` VARCHAR(20) DEFAULT NULL,
  `image_url` VARCHAR(100) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `last_updated_by` varchar(255) DEFAULT NULL,
  `level` bigint DEFAULT 0,
  `uid` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`),
  UNIQUE (`uid`),
  KEY idx_updated_on (updated_on),
  KEY idx_uid (uid),
  KEY idx_parent(parent)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
