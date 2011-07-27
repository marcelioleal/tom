#Script para apagar dados estruturados
TRUNCATE `actor`;
TRUNCATE `artifact`;
TRUNCATE `author`;
TRUNCATE `classItem`;
TRUNCATE `collaboration`;
TRUNCATE `item`;
TRUNCATE `link`;
TRUNCATE `requeriment`;
TRUNCATE `useCase`;
UPDATE  `tom`.`project` SET  `status` =  'U' WHERE  `project`.`idProject` =1 LIMIT 1 ;
