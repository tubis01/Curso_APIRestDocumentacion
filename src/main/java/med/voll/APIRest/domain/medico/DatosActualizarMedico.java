package med.voll.APIRest.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.APIRest.domain.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull
        Long id,
        String nombre,
        String documento,
        DatosDireccion direccion
) {
}
