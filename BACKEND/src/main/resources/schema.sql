CREATE TABLE tipo (
    id INT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    usos_comunes TEXT,
    interacciones TEXT
);

CREATE TABLE medicamento (
    id INT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    dosis_recomendada VARCHAR(255),
    laboratorio VARCHAR(255),
    viaAdministracion VARCHAR(255),
    contraindicaciones TEXT,
    precio DECIMAL(10, 2),
    tipo_id INT,
    FOREIGN KEY (tipo_id) REFERENCES tipo(id)
);