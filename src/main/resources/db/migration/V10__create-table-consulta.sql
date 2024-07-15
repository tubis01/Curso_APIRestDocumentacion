
CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    medico_id INTEGER NOT NULL,
    paciente_id INTEGER NOT NULL,
    fechaConsulta TIMESTAMP NOT NULL,
    FOREIGN KEY (medico_id) REFERENCES medicos(id),
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
)