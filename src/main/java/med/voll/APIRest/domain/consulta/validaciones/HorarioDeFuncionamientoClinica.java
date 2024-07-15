package med.voll.APIRest.domain.consulta.validaciones;

import med.voll.APIRest.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorConsultas {

    public void validar(DatosAgendarConsulta datosAgendarConsulta){

        boolean domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fechaConsulta().getDayOfWeek());
        boolean antesDeApertura = datosAgendarConsulta.fechaConsulta().getHour()<7;
        boolean despuesDeCierre = datosAgendarConsulta.fechaConsulta().getHour()>19;

        if(domingo || antesDeApertura || despuesDeCierre){
            throw new RuntimeException("El horario de atencion de la clinica es de lunes a sabado de 7 a 19hs");
        }
    }
}
