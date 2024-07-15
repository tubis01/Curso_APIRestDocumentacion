package med.voll.APIRest.infra.errores;

public class ValidacionesDeIntegridad extends RuntimeException {
    public ValidacionesDeIntegridad(String pacienteNoEncontrado) {

        super(pacienteNoEncontrado);
    }
}
