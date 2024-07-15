package med.voll.APIRest.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.APIRest.domain.medico.Medico;
import med.voll.APIRest.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Entity(name = "consulta")
@Table(name = "consultas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column(name = "fechaconsulta")
    private LocalDateTime fechaConsulta;

    @Column(name = "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamientoConsulta motivo;

    public Consulta(Medico medico, Paciente paciente, LocalDateTime localDateTime) {
        this.medico = medico;
        this.paciente = paciente;
        this.fechaConsulta = localDateTime;
    }


    public void cancelarConsulta(MotivoCancelamientoConsulta motivo) {
        this.motivo = motivo;
    }
}
