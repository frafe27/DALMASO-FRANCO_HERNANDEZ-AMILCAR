DROP TABLE IF EXISTS ODONTOLOGOS;
CREATE TABLE ODONTOLOGOS (ID LONG AUTO_INCREMENT PRIMARY KEY,NUMERO_MATRICULA INT NOT NULL,NOMBRE VARCHAR(40) NOT NULL,APELLIDO VARCHAR(40) NOT NULL);
INSERT INTO ODONTOLOGOS (NUMERO_MATRICULA,NOMBRE,APELLIDO) VALUES (12345, "Juan", "Pérez"),(67890, "María", "Gómez"),(11223, "Carlos", "Rodríguez"),(33445, "Ana", "López"),(55667, "Luis", "Martínez");