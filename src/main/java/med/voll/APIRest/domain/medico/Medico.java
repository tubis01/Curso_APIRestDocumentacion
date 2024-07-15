package med.voll.APIRest.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.APIRest.domain.direccion.Direccion;

@Entity(name = "medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private boolean activo ;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico registroMedico) {
        this.activo = true;
        this.nombre = registroMedico.nombre();
        this.email = registroMedico.email();
        this.telefono = registroMedico.telefono();
        this.documento = registroMedico.documento();
        this.especialidad = registroMedico.especialidad();
        this.direccion = new Direccion(registroMedico.direccion());

    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if(datosActualizarMedico.nombre() != null){
            this.nombre = datosActualizarMedico.nombre();
        }
        if(datosActualizarMedico.documento() != null){
            this.documento = datosActualizarMedico.documento();
        }
        if(datosActualizarMedico.direccion() != null){
            this.direccion = new Direccion(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
