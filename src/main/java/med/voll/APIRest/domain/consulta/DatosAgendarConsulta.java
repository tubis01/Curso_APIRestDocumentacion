package med.voll.APIRest.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.APIRest.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        @Future
        LocalDateTime fechaConsulta,
        Especialidad especialidad
) {
}
