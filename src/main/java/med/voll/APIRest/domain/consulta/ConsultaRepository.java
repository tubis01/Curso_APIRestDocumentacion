package med.voll.APIRest.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {



    Boolean existsByPacienteIdAndFechaConsultaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFechaConsulta(Long idMedico, LocalDateTime fechaConsulta);
}
