package med.voll.APIRest.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.APIRest.domain.consulta.ConsultaRepository;
import med.voll.APIRest.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PacienteSinConsulta  implements ValidadorConsultas {

    private final ConsultaRepository consultaRepository;

    public PacienteSinConsulta(ConsultaRepository consultaRepository){
        this.consultaRepository = consultaRepository;
    }


    public void validar(DatosAgendarConsulta datosAgendarConsulta){

        LocalDateTime primerHorario = datosAgendarConsulta.fechaConsulta().withHour(7);
        LocalDateTime ultimoHorario = datosAgendarConsulta.fechaConsulta().withHour(18);

        Boolean PacienteConconsulta = consultaRepository.existsByPacienteIdAndFechaConsultaBetween(datosAgendarConsulta.idPaciente(), primerHorario, ultimoHorario);

        if ( PacienteConconsulta ) {
            throw new ValidationException("No se puede permitir agendar citas con pacientes que ya tienen una consulta agendada en el mismo horario");
        }
    }
}
