CREATE SCHEMA `smartfort` ;

CREATE TABLE `smartfort`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `smartfort`.`user` (`first_name`, `last_name`, `email`, `date_of_birth`) VALUES ('Ivan', 'Ivanov', 'ivan@example.com', '1999-09-09');
INSERT INTO `smartfort`.`user` (`first_name`, `last_name`, `email`, `date_of_birth`) VALUES ('Petr', 'Petrov', 'petr@email@ru', '2000-10-10');


