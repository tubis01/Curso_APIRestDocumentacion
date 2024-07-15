package med.voll.APIRest.domain.medico;

import med.voll.APIRest.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String telefono,
        String documento,
        String email,
        DatosDireccion direccion
) {
}
