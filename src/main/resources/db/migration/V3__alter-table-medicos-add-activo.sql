
ALTER TABLE medicos ADD activo SMALLINT;

UPDATE  medicos SET activo = 1;