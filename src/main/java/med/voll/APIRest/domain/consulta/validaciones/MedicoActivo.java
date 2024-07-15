package med.voll.APIRest.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.APIRest.domain.consulta.DatosAgendarConsulta;
import med.voll.APIRest.domain.medico.MedicoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MedicoActivo implements ValidadorConsultas {

    private final MedicoRepository medicoRepository;

    public MedicoActivo(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        if(datosAgendarConsulta.idMedico() == null){
            return;
        }

        Boolean medicoActivo = medicoRepository.findActivoById(datosAgendarConsulta.idMedico());

        if(!medicoActivo) throw new ValidationException("No se puede permitir agendar citas con medicos inactivos");
    }
}
