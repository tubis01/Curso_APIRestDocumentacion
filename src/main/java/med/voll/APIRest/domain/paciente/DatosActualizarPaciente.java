package med.voll.APIRest.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.APIRest.domain.direccion.DatosDireccion;

public record DatosActualizarPaciente(
        @NotNull
        Long id,
        String nombre,
        String documento,
        DatosDireccion direccion
) {
}
