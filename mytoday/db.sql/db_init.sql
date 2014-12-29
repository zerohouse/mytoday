-- ----------------------------------------------------------------
-- DB and User Setup
-- ----------------------------------------------------------------
--DROP DATABASE IF EXISTS `realrank`;
--CREATE DATABASE `realrank` DEFAULT CHARACTER SET utf8;
--
--USE `mysql`;
--DELETE FROM `user` WHERE User='realrank_test';
--DELETE FROM `db` WHERE User='realrank_test';
--FLUSH PRIVILEGES;
--
--CREATE USER 'realrank_test'@'%' IDENTIFIED BY '1234';
--CREATE USER 'realrank_test'@'localhost' IDENTIFIED BY '1234';
--GRANT ALL PRIVILEGES ON `realrank`.* TO 'realrank_test'@'%';
--GRANT ALL PRIVILEGES ON `realrank`.* TO 'realrank_test'@'localhost';
--FLUSH PRIVILEGES;

-- ----------------------------------------------------------------
-- Table Setup
-- ----------------------------------------------------------------
DROP TABLE IF EXISTS `user`;

-- Table `user`
CREATE TABLE `user` (
	`id` VARCHAR(32) NOT NULL,
	`password` VARCHAR(32) NULL,
	`email` VARCHAR(64) NULL,
	`nickname` VARCHAR(64) NULL,
	`gender` CHAR(1) NULL,
	`timestamp` DATETIME NOT NULL,
	PRIMARY KEY(`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `schedule`;

-- Table `schedule`
CREATE TABLE `schedule` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`date` DATE NULL,
	`userId` VARCHAR(32) NULL,
	`type` VARCHAR(32) NULL,
	`time` TINYINT NULL,
	`startTime` TINYINT NULL,
	`head` VARCHAR(32) NULL,
	`body` VARCHAR(2000) NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


DROP TABLE IF EXISTS `type`;

-- Table `type`
CREATE TABLE `type` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`userId` VARCHAR(32) NULL,
	`name` VARCHAR(32) NULL,
	`color` VARCHAR(32) NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

--
--
--DROP TABLE IF EXISTS `done`;
--
---- Table `user`
--CREATE TABLE `done` (
--	`id` INTEGER NOT NULL AUTO_INCREMENT,
--	`duration` TINYINT NOT NULL,
--	`startTime` DATETIME NOT NULL,
--	`req_time` DATETIME NOT NULL,
--	`acc_time` DATETIME NULL,
--	`state` TINYINT NULL,
--	`winner` VARCHAR(32) NULL,
--	PRIMARY KEY(`id`)
--) ENGINE = InnoDB;
--
--
--DROP TABLE IF EXISTS `score`;
--
---- Table `score`
--CREATE TABLE `score` (
--	`id` VARCHAR(32) NOT NULL,
--	`score` TINYINT NOT NULL,
--	PRIMARY KEY(`id`)
--) ENGINE = InnoDB;
