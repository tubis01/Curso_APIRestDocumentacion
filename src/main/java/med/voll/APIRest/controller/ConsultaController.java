package med.voll.APIRest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.APIRest.domain.consulta.AgendarConsultaService;
import med.voll.APIRest.domain.consulta.DatosAgendarConsulta;
import med.voll.APIRest.domain.consulta.DatosCancelamientoConsulta;
import med.voll.APIRest.domain.consulta.DatosDetalleConsulta;
import med.voll.APIRest.infra.errores.ValidacionesDeIntegridad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final AgendarConsultaService consultaService;

    public ConsultaController(AgendarConsultaService agendarConsultaService) {
        this.consultaService = agendarConsultaService;
    }

    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity<DatosDetalleConsulta> registrarConsulta(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta) throws ValidacionesDeIntegridad {

        DatosDetalleConsulta datosDetalleConsulta = consultaService.agendarConsulta(datosAgendarConsulta);
        return ResponseEntity.ok(datosDetalleConsulta);
    }

    @GetMapping("/listar")
    public ResponseEntity <Page<DatosDetalleConsulta>> listarConsular(@PageableDefault(sort = "fechaConsulta") Pageable paginacion){
    var consultas = consultaService.listarConsultas(paginacion);
    return ResponseEntity.ok(consultas);
    }


    @DeleteMapping("/cancelar")
    @Transactional
    public ResponseEntity<DatosDetalleConsulta> cancelarConsulta(@RequestBody @Valid DatosCancelamientoConsulta datosCancelarConsulta){
        consultaService.cancelarConsulta(datosCancelarConsulta);
        return ResponseEntity.ok().build();
    }
}
