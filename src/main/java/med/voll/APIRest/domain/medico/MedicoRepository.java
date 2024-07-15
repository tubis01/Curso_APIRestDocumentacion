package med.voll.APIRest.domain.medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {


    Page<Medico>  findByActivoTrue(Pageable paginacion);

    @Query("Select m from medico m where m.activo = true and m.especialidad = :especialidad and m.id not in (select c.medico.id from consulta c where c.fechaConsulta= :fecha) order by random() limit 1")
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

//    @Query("select m.activo from medico m where m.activo = true and m.id = :idMedico")
    @Query("select m.activo from medico m where m.id = :idMedico")
    Boolean findActivoById(Long idMedico);
}
