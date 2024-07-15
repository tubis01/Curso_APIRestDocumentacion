package med.voll.APIRest.domain.consulta.validaciones;

import med.voll.APIRest.domain.consulta.Consulta;
import med.voll.APIRest.domain.consulta.ConsultaRepository;
import med.voll.APIRest.domain.consulta.DatosCancelamientoConsulta;
import med.voll.APIRest.infra.errores.ValidacionesDeIntegridad;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValoraHorarioAntecendecia implements  ValidadorCancelamientoConsulta {

    private final ConsultaRepository consultaRepository;

    public ValoraHorarioAntecendecia(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void validar(DatosCancelamientoConsulta datos) {

        Consulta consulta = consultaRepository.getReferenceById(datos.idConsulta());

        LocalDateTime momentoActual = LocalDateTime.now();

        long diferenciaEnHoras = Duration.between(momentoActual, consulta.getFechaConsulta()).toHours();

        if (diferenciaEnHoras < 24) {
            throw new ValidacionesDeIntegridad("No se puede cancelar la consulta con menos de 24 horas de antelación");
        }
    }
}
