package med.voll.APIRest.domain.consulta;

import med.voll.APIRest.domain.consulta.validaciones.ValidadorCancelamientoConsulta;
import med.voll.APIRest.domain.consulta.validaciones.ValidadorConsultas;
import med.voll.APIRest.domain.medico.Medico;
import med.voll.APIRest.domain.medico.MedicoRepository;
import med.voll.APIRest.domain.paciente.Paciente;
import med.voll.APIRest.domain.paciente.PacienteRepository;
import med.voll.APIRest.infra.errores.ValidacionesDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendarConsultaService {

    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    private final List<ValidadorConsultas> validadores;
    private final List<ValidadorCancelamientoConsulta> validadorCancelamientoConsultas;

    public AgendarConsultaService(MedicoRepository medicoRepository, PacienteRepository pacienteRepository, ConsultaRepository consultaRepository, List<ValidadorConsultas> validadores, List<ValidadorCancelamientoConsulta> validadorCancelamientoConsultas) {
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.validadores = validadores;
        this.validadorCancelamientoConsultas = validadorCancelamientoConsultas;
    }

    public DatosDetalleConsulta agendarConsulta(DatosAgendarConsulta datos) {

        if(pacienteRepository.findById(datos.idPaciente()).isEmpty()){
            throw new ValidacionesDeIntegridad("Paciente no encontrado");
        }
        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionesDeIntegridad("Medico no encontrado");
        }

        validadores.forEach(validador -> validador.validar(datos));
        Medico medico =  seleccionarMedico(datos);

        if(medico == null){
            throw new ValidacionesDeIntegridad("El medico No existe disponible para este horario y especialidad");
        }

        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).orElseThrow();
        Consulta consulta = new Consulta(medico, paciente, datos.fechaConsulta()) ;
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if(datos.especialidad() == null){
            throw new ValidacionesDeIntegridad("Debe seleccionar una especialidad ");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fechaConsulta());
    }

    public void cancelarConsulta(DatosCancelamientoConsulta datosCancelarConsulta) {
        if(!consultaRepository.existsById(datosCancelarConsulta.idConsulta())){
            throw new ValidacionesDeIntegridad("Id de la consulta no existe");
        }

        validadorCancelamientoConsultas.forEach(validador -> validador.validar(datosCancelarConsulta));

        Consulta consulta = consultaRepository.getReferenceById(datosCancelarConsulta.idConsulta());
        consulta.cancelarConsulta(datosCancelarConsulta.motivo());

    }

    public Page<DatosDetalleConsulta> listarConsultas(Pageable paginacion) {
        return consultaRepository.findAll(paginacion).map(DatosDetalleConsulta::new);
    }
}
