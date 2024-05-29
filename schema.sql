-- CREACIÓN ODONTOLOGOS
DROP TABLE IF EXISTS ODONTOLOGOS;
CREATE TABLE ODONTOLOGOS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NUMERO_MATRICULA INT NOT NULL,
    NOMBRE VARCHAR(40) NOT NULL,
    APELLIDO VARCHAR(40) NOT NULL
);
INSERT INTO ODONTOLOGOS (NUMERO_MATRICULA, NOMBRE, APELLIDO) VALUES
(12345, 'Sofía', 'Méndez'),
(67890, 'Pedro', 'Sánchez'),
(11223, 'Lucía', 'Vega'),
(33445, 'Fernando', 'García'),
(55667, 'Marta', 'Torres');
-- FIN ODONTOLOGOS

-- CREACIÓN DOMICILIOS
DROP TABLE IF EXISTS DOMICILIOS;
CREATE TABLE DOMICILIOS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    DIRECCION VARCHAR(255) NOT NULL,
    CIUDAD VARCHAR(100) NOT NULL,
    PROVINCIA VARCHAR(100) NOT NULL,
    PAIS VARCHAR(100) NOT NULL
);

INSERT INTO DOMICILIOS (DIRECCION, CIUDAD, PROVINCIA, PAIS) VALUES
('Calle Falsa 123', 'Springfield', 'Illinois', 'USA'),
('Avenida Siempre Viva 742', 'Springfield', 'Illinois', 'USA'),
('Calle Luna 456', 'Capital Federal', 'Buenos Aires', 'Argentina'),
('Boulevard de los Sueños 789', 'Monterrey', 'Nuevo León', 'México'),
('Ruta 66', 'Kingman', 'Arizona', 'USA');
-- FIN DOMICILIOS

-- CREACIÓN PACIENTES
DROP TABLE IF EXISTS PACIENTES;
CREATE TABLE PACIENTES (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(40) NOT NULL,
    APELLIDO VARCHAR(40) NOT NULL,
    DNI VARCHAR(20) NOT NULL UNIQUE,
    FECHA DATE NOT NULL,
    DOMICILIO_ID INT,
    FOREIGN KEY (DOMICILIO_ID) REFERENCES DOMICILIOS(ID)
);
INSERT INTO PACIENTES (NOMBRE, APELLIDO, DNI, FECHA, DOMICILIO_ID) VALUES
('Gabriel', 'Silva', '12345678', '2024-04-01', 1),
('Elena', 'Ruiz', '23456789', '2024-04-10', 2),
('Miguel', 'Navarro', '34567890', '2024-04-20', 3),
('Alicia', 'Castro', '45678901', '2024-04-25', 4),
('Pablo', 'Ortiz', '56789012', '2024-04-30', 5);
-- FIN PACIENTES

-- CREACIÓN TURNOS
DROP TABLE IF EXISTS TURNOS;
CREATE TABLE TURNOS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    PACIENTE_ID INT NOT NULL,
    ODONTOLOGO_ID INT NOT NULL,
    FECHAYHORA DATETIME NOT NULL,
    FOREIGN KEY (PACIENTE_ID) REFERENCES PACIENTES(ID),
    FOREIGN KEY (ODONTOLOGO_ID) REFERENCES ODONTOLOGOS(ID)
);
INSERT INTO TURNOS (PACIENTE_ID, ODONTOLOGO_ID, FECHAYHORA) VALUES
(1, 1, '2024-06-01 09:00:00'),
(2, 2, '2024-06-01 10:00:00'),
(3, 3, '2024-06-01 11:00:00'),
(4, 4, '2024-06-01 12:00:00'),
(5, 5, '2024-06-01 13:00:00');
-- FIN TURNOS