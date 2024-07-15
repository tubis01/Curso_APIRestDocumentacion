package med.voll.APIRest.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.APIRest.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorConsultas {
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        LocalDateTime ahoraActual = LocalDateTime.now();
        LocalDateTime horaDeConsulta = datosAgendarConsulta.fechaConsulta();

        boolean DiferenciaDe30Minutos = Duration.between(ahoraActual, horaDeConsulta).toMinutes() < 30;

        if(DiferenciaDe30Minutos){
            throw new ValidationException("La consulta debe ser agendada con al menos 30 minutos de anticipacion");
        }
    }
}
