package med.voll.APIRest.domain.direccion;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    String calle;
    String distrito;
    String ciudad;
    String numero;
    String complemento;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.distrito = direccion.distrito();
        this.ciudad = direccion.ciudad();
        this.numero = direccion.numero();
        this.complemento = direccion.complemento();
    }

    public Direccion ActualizarDatos(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.distrito = direccion.distrito();
        this.ciudad = direccion.ciudad();
        this.numero = direccion.numero();
        this.complemento = direccion.complemento();
        return this;
    }
}
