package org.example.ssoauth2.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;


@Component
public class JWTUtils {

    public String generateToken(String username) {
        Date exDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(exDate)
                .sign(Algorithm.HMAC256("123"));
    }

    public String validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("123"))
                .withSubject("User details")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
