package med.voll.APIRest.domain.paciente;

import med.voll.APIRest.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente(
        Long id,
        String nombre,
        String telefono,
        String documento,
        String email,
        DatosDireccion direccion
) {
}
