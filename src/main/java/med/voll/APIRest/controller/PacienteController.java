package med.voll.APIRest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.APIRest.domain.direccion.DatosDireccion;
import med.voll.APIRest.domain.paciente.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    private final PacienteRepository pacienteRepository;


    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    //obtener todos los pacientes
    @GetMapping("/listar")
    public ResponseEntity<Page<DatosListadoPaciente>>listarPacientes(@PageableDefault(sort = "documento") Pageable paginacion) {
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
    }

    //guardar paciente
    @PostMapping("/registrar")
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente (@RequestBody @Valid DatosRegistroPaciente registrPaciente,
                                                                     UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(registrPaciente));

        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getDocumento(),
                paciente.getEmail(),
                new DatosDireccion(
                        paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getComplemento()
                )
        );

        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return  ResponseEntity.created(uri).body(datosRespuestaPaciente);

    }

    //obtener paciente por id
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> obtenerPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaPaciente(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getDocumento(),
                paciente.getEmail(),
                new DatosDireccion(
                        paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getComplemento()
                )
        ));
    }

    //actualizar paciente
    @PutMapping("/actualizar")
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaiente (@RequestBody @Valid DatosActualizarPaciente actualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(actualizarPaciente.id());
        paciente.actualizarDatos(actualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getDocumento(),
                paciente.getEmail(),
                new DatosDireccion(
                        paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getComplemento()
                )
        ));
    }

    //eliminar paciente logicamente
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

}
