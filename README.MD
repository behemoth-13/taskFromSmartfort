Тестовое задание smartfort.org
==============================

Для создания БД используйте скрипт 

>CREATE SCHEMA \`smartfort\` ;
>
>CREATE TABLE \`smartfort\`.\`user\` (  
  \`id\` INT NOT NULL AUTO_INCREMENT,  
  \`first_name\` VARCHAR(45) NOT NULL,  
  \`last_name\` VARCHAR(45) NOT NULL,  
  \`email\` VARCHAR(45) NOT NULL,  
  \`date_of_birth\` DATE NOT NULL,  
  PRIMARY KEY (\`id\`));

>INSERT INTO \`smartfort\`.\`user\` (\`first_name\`, \`last_name\`, \`email\`, \`date_of_birth\`)  
> VALUES ('Ivan', 'Ivanov', 'ivan@example.com', '1999-09-09');  
>INSERT INTO \`smartfort\`.\`user\` (\`first_name\`, \`last_name\`, \`email\`, \`date_of_birth\`)  
> VALUES ('Petr', 'Petrov', 'petr@email@ru', '2000-10-10');
 
или запустите createSchema.sql

настройки БД в приложении находятся в файле mybatis-config.xml

далее в командной строке:

>     mvn clean compile exec:java

для анализа работы используйте bash

curl -i -H "Content-Type: application/json" -X POST -d '{"id":0,"firstName":"Alex","lastName":"Alexeev","email":"alex@yandex.ru","dateOfBirth":"27.08.1999"}' http://localhost:8080/user  
curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/user/1  
curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/user  
curl -i -H "Content-Type: application/json" -X PUT -d '{"id":0,"firstName":"Ivan","lastName":"Petrov","email":"notalex@yandex.ru","dateOfBirth":"27.08.1989"}' http://localhost:8080/user  
curl -i -X DELETE http://localhost:8080/user/1
