-- Insert into tipo table
INSERT INTO tipo (id, nombre, descripcion, usos_comunes, interacciones) VALUES
(1, 'Calmante', 'Medicamentos que ayudan a reducir la ansiedad.', 'Trastornos de ansiedad.', 'Interacción con alcohol.'),
(2, 'Antiinflamatorio', 'Medicamentos que reducen la inflamación.', 'Artritis, esguinces.', 'Interacción con corticoides.'),
(3, 'Antibiótico', 'Medicamentos que combaten infecciones bacterianas.', 'Infecciones bacterianas.', 'Interacción con anticoagulantes.'),
(4, 'Antipirético', 'Medicamentos que reducen la fiebre.', 'Fiebre.', 'Interacción con otros antipiréticos.'),
(5, 'Antihistamínico', 'Medicamentos para tratar alergias.', 'Reacciones alérgicas.', 'Interacción con sedantes.');

-- Insert into medicamento table
INSERT INTO medicamento (id, nombre, descripcion, dosis_recomendada, laboratorio, viaAdministracion, contraindicaciones, precio, tipo_id) VALUES
(1, 'Ibuprofeno', 'Antiinflamatorio no esteroideo.', '200mg cada 8 horas', 'Lab A', 'Oral', 'No usar en úlceras gástricas.', 12.99, 2),
(2, 'Diazepam', 'Ansiolítico utilizado para el tratamiento de la ansiedad.', '5mg cada 8 horas', 'Lab B', 'Oral', 'No usar con alcohol.', 15.50, 1),
(3, 'Amoxicilina', 'Antibiótico de amplio espectro.', '500mg cada 8 horas', 'Lab C', 'Oral', 'No usar en pacientes con alergia a penicilina.', 8.99, 3),
(4, 'Paracetamol', 'Antipirético utilizado para reducir fiebre y dolor.', '500mg cada 6 horas', 'Lab D', 'Oral', 'No exceder la dosis recomendada.', 5.99, 4),
(5, 'Loratadina', 'Antihistamínico para tratar alergias.', '10mg una vez al día', 'Lab E', 'Oral', 'No usar en combinación con alcohol.', 7.99, 5),
(6, 'Aspirina', 'Analgesico y antipirético.', '500mg cada 6 horas', 'Lab F', 'Oral', 'No usar en niños con fiebre.', 9.99, 2),
(7, 'Sertralina', 'Antidepresivo ISRS.', '50mg una vez al día', 'Lab G', 'Oral', 'No usar con inhibidores de MAO.', 25.00, 1),
(8, 'Levonorgestrel', 'Anticonceptivo de emergencia.', '1 tableta en las primeras 72 horas', 'Lab H', 'Oral', 'No usar si está embarazada.', 30.00, 2),
(9, 'Warfarina', 'Anticoagulante para prevenir coágulos.', '5mg una vez al día', 'Lab I', 'Oral', 'Interacción con vitamina K.', 20.00, 3),
(10, 'Metformina', 'Antidiabético para controlar la glucosa.', '500mg dos veces al día', 'Lab J', 'Oral', 'No usar en insuficiencia renal.', 18.00, 4),
(11, 'Naproxeno', 'Antiinflamatorio no esteroideo.', '250mg cada 8 horas', 'Lab K', 'Oral', 'No usar en pacientes con úlceras.', 13.99, 2),
(12, 'Clonazepam', 'Ansiolítico para tratar ataques de pánico.', '1mg dos veces al día', 'Lab L', 'Oral', 'No usar con alcohol.', 22.50, 1);