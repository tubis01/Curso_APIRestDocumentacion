package med.voll.APIRest.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamientoConsulta motivo
) {

}
