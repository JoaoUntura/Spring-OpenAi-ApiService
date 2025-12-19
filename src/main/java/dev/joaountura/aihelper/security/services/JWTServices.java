package dev.joaountura.aihelper.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.joaountura.aihelper.models.UserApp;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JWTServices {
    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    protected String secret;

    public int duration = 10800 ;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(this.secret);
    }


    public String encodeToken(UserApp user){
        Instant expirationTime = Instant.now().plusSeconds(this.duration);
        return JWT.create()
                .withIssuer(this.issuer)
                .withSubject(user.getId().toString())
                .withExpiresAt(expirationTime)
                .sign(this.algorithm);
    }


    public DecodedJWT verifyAndDecode(String token) throws ServletException {
        JWTVerifier verifier = JWT.require(this.algorithm).withIssuer(issuer).build();


        return verifier.verify(token);

    }


}
