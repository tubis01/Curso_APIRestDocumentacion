package med.voll.APIRest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.APIRest.domain.direccion.DatosDireccion;
import med.voll.APIRest.domain.medico.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }


    @GetMapping
    public ResponseEntity< Page<DatosListadoMedico> >listarMedicos(@PageableDefault(sort = "documento") Pageable paginacion) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    //con este metodo se obtiene un medico por su id para mostrarlo en la respuesta de la peticion
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> obtenerMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getDocumento(),
                medico.getEmail(),
                medico.getEspecialidad().name(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getComplemento())
        ));
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico registroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(registroMedico));

        //Aqui se crea la respuesta con DatosRespuestaMedico porque de esta manera se devuelve la direccion completa
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getDocumento(),
                medico.getEmail(),
                medico.getEspecialidad().name(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getComplemento())
        );

        //Esto es para devolver la url de la respuesta en el header
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);

        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getDocumento(),
                medico.getEmail(),
                medico.getEspecialidad().name(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getComplemento())
        ));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Medico> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }
//    }    public void eliminarMedico( @PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }

}
