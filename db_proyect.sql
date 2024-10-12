-- create database db_proyect;
use db_proyect;
create table users(
`id_cliente` INT NOT NULL  AUTO_INCREMENT,
`user_name` varchar(45) NOT NULL,
`phone` INT NOT NULL,
`rol` varchar(45) NOT NULL,
`password` varchar(16) NOT NULL, 
primary key(`id_cliente`)
);