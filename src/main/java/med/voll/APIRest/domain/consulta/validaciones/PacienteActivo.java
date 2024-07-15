package med.voll.APIRest.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.APIRest.domain.consulta.DatosAgendarConsulta;
import med.voll.APIRest.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorConsultas {

    private final PacienteRepository pacienteRepository;

    public PacienteActivo(PacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {

        if (datosAgendarConsulta.idPaciente() == null) {
            return;
        }

        Boolean pacienteActivo = pacienteRepository.findActivoById(datosAgendarConsulta.idPaciente());

        if (!pacienteActivo) {
            throw new ValidationException("No se puede permitir agendar citas con pacientes inactivos");
        }

    }
}
