package med.voll.APIRest.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    Page<Paciente> findByActivoTrue(Pageable paginacion);


//    @Query("select p.activo from paciente p where p.activo = true and p.id = :idPaciente")
    @Query("select p.activo from paciente p where  p.id = :idPaciente")
    Boolean findActivoById(Long idPaciente);
}
