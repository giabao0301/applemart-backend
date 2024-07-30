package com.applemart.backend.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
@Service
public class JWTUtil {

    @NonFinal
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String issueToken(String subject, String ...scopes) {
        return issueToken(subject, Map.of("scope", scopes));
    }

    public String issueToken(String subject, List<String> scopes) {
        return issueToken(subject, Map.of("scope", scopes));
    }

    public String issueToken(String subject, Map<String, Object> claims) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer("https://applemart.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(7, DAYS).toEpochMilli()
                ));


        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            claimsSetBuilder.claim(entry.getKey(), entry.getValue());
        }

        JWTClaimsSet claimsSet = claimsSetBuilder.build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e);
        }
    }


    public SignedJWT verifyToken(String token)
            throws ParseException, JOSEException {

        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new BadCredentialsException("Invalid token");
        }

        return signedJWT;
    }
}
