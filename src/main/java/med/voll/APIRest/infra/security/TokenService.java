package med.voll.APIRest.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.APIRest.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("$(api.security.secret)")
    private String apiSecret;

    public String generaToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return  JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al crear el token");
        }
    }

    public String getSubject(String token){
        DecodedJWT verifier = null;

        try {
            verifier = JWT.require(Algorithm.HMAC256(apiSecret))
                    .withIssuer("voll med")
                    .build()
                    .verify(token);
            verifier.getSubject();
            System.out.println(token);
        } catch (Exception e) {
            throw new RuntimeException("Token no valido");
        }

        if(verifier.getSubject() == null){
            throw new RuntimeException("token invalido");
        }
        return verifier.getSubject();
    }


    private Instant generarFechaExpiracion(){
        return LocalDate.now().plusWeeks(1).atStartOfDay().toInstant(ZoneOffset.UTC)    ;
    }
}
