package med.voll.APIRest.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.APIRest.domain.consulta.ConsultaRepository;
import med.voll.APIRest.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorConsultas {

    private final ConsultaRepository consultaRepository;

    public MedicoConConsulta(ConsultaRepository consultaRepository){
        this.consultaRepository = consultaRepository;
    }

    public void validar(DatosAgendarConsulta datosAgendarConsulta){

        if(datosAgendarConsulta.idMedico() == null)
            return;

        Boolean medicoConConsulta = consultaRepository.existsByMedicoIdAndFechaConsulta(datosAgendarConsulta.idMedico(), datosAgendarConsulta.fechaConsulta());

        if(medicoConConsulta){
            throw new ValidationException("Este medico ya tiene una consulta agendada en el mismo horario");
        }
    }
}
