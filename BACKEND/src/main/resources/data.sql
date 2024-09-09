INSERT INTO tipo (id, nombre, descripcion, usos_comunes, interacciones) VALUES
(1, 'Calmante', 'Medicamentos que ayudan a reducir la ansiedad.', 'Trastornos de ansiedad.', 'Interacción con alcohol.'),
(2, 'Antiinflamatorio', 'Medicamentos que reducen la inflamación.', 'Artritis, esguinces.', 'Interacción con corticoides.');

INSERT INTO medicamento (id, nombre, descripcion, dosis_recomendada, laboratorio, viaAdministracion, contraindicaciones, precio, tipo_id) VALUES
(1, 'Ibuprofeno', 'Antiinflamatorio no esteroideo.', '200mg cada 8 horas', 'Lab A', 'Oral', 'No usar en úlceras gástricas.', 12.99, 2),
(2, 'Diazepam', 'Ansiolítico utilizado para el tratamiento de la ansiedad.', '5mg cada 8 horas', 'Lab B', 'Oral', 'No usar con alcohol.', 15.50, 1);