package med.voll.APIRest.controller;

import jakarta.validation.Valid;
import med.voll.APIRest.domain.usuarios.DatosAutenticacionUsuario;
import med.voll.APIRest.domain.usuarios.Usuario;
import med.voll.APIRest.infra.security.DatosJWTtoken;
import med.voll.APIRest.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AutenticacionController(AuthenticationManager manager, TokenService tokenService) {
        this.authenticationManager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity auntenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());

        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

        String JWTtoken = tokenService.generaToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));// asi nos devuelve el token en el body con formato JSON
    }

}
