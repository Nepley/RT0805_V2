package org.hdanyel.commun;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.Calendar;


public class GestionJWT {

    public static String generateToken(int id, String user){
            
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();        
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date expire = calendar.getTime();

            String token = new String("");
            try {
                Algorithm algorithm = Algorithm.HMAC256("RT0805");
                token = JWT.create()
                    .withClaim("login", user)
                    .withClaim("id", Integer.toString(id))
                    .withIssuedAt(now)
                    .withExpiresAt(expire)
                    .withIssuer("Tracking")
                    .sign(algorithm);
            } catch (JWTCreationException exception){
                System.out.println("Erreur dans la création du token" + exception);
                System.exit(0);
            }
            return token;
        }

    public static void verifyToken(String token){
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("RT0805");
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Tracking")
                .build();
            jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            System.out.println("Erreur lors de la vérif" + exception);
            System.exit(0);
        }  
    }

    public static DecodedJWT decodeToken(String token) {
        DecodedJWT jwt = null;
        try {
            jwt = JWT.decode(token);
        } catch (JWTDecodeException exception){
            System.out.println("Erreur lors du décodage");
            System.exit(0);
        }

        return jwt;
    }
}