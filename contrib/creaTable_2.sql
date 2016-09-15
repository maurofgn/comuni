-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema comuni
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema comuni
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `comuni` DEFAULT CHARACTER SET utf8 ;
USE `comuni` ;

DROP TABLE IF EXISTS `comuni`.`hibernate_sequence` ;

-- -----------------------------------------------------
-- Table `comuni`.`regione`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comuni`.`regione` ;

CREATE TABLE IF NOT EXISTS `comuni`.`regione` (
  `regione_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `nome` VARCHAR(80) NULL DEFAULT NULL COMMENT '',
  `nose` VARCHAR(15) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`regione_id`)  COMMENT '',
  INDEX `regione_nome` (`nome` ASC)  COMMENT '')
ENGINE = InnoDB
--AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `comuni`.`provincia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comuni`.`provincia` ;

CREATE TABLE IF NOT EXISTS `comuni`.`provincia` (
  `provincia_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `nome` VARCHAR(80) NULL DEFAULT NULL COMMENT '',
  `sigla` VARCHAR(5) NULL DEFAULT NULL COMMENT '',
  `regione_id` INT(11) NULL DEFAULT NULL COMMENT '',
  `capoluogo` INT(11) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`provincia_id`)  COMMENT '',
  INDEX `provincia_nome` (`nome` ASC)  COMMENT '',
  CONSTRAINT `FKtlfmnamch6yqxj9ljcwhuooow`
    FOREIGN KEY (`regione_id`)
    REFERENCES `comuni`.`regione` (`regione_id`))
ENGINE = InnoDB
--AUTO_INCREMENT = 111
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `comuni`.`comune`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comuni`.`comune` ;

CREATE TABLE IF NOT EXISTS `comuni`.`comune` (
  `comune_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `nome` VARCHAR(80) NULL DEFAULT NULL COMMENT '',
  `codice_catastale` VARCHAR(4) NULL DEFAULT NULL COMMENT '',
  `abitanti` INT(11) NULL DEFAULT NULL COMMENT '',
  `capoluogo` TINYINT(1) NULL DEFAULT NULL COMMENT '',
  `provincia_id` INT(11) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`comune_id`)  COMMENT '',
  INDEX `comune_nome` (`nome` ASC)  COMMENT '',
  CONSTRAINT `comune_ibfk_1`
    FOREIGN KEY (`provincia_id`)
    REFERENCES `comuni`.`provincia` (`provincia_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
--AUTO_INCREMENT = 8004
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
