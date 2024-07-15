package med.voll.APIRest.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.APIRest.domain.direccion.Direccion;
import med.voll.APIRest.domain.medico.DatosActualizarMedico;

@Entity(name = "paciente")
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String telefono;

    private String documento;

    @Embedded
    private Direccion direccion;

    private boolean activo;

    public Paciente(DatosRegistroPaciente registroPaciente) {
        this.activo = true;
        this.nombre = registroPaciente.nombre();
        this.email = registroPaciente.email();
        this.telefono = registroPaciente.telefono();
        this.documento = registroPaciente.documento();
        this.direccion = new Direccion(registroPaciente.direccion());
    }

    public void actualizarDatos(DatosActualizarPaciente datosActualizarPaciente ){
        if(datosActualizarPaciente.nombre() != null){
            this.nombre = datosActualizarPaciente.nombre();
        }
        if(datosActualizarPaciente.documento() != null){
            this.documento = datosActualizarPaciente.documento();
        }
        if(datosActualizarPaciente.direccion() != null){
            this.direccion = new Direccion(datosActualizarPaciente.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}
