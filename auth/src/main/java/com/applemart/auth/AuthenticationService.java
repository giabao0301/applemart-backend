package com.applemart.auth;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request) throws ParseException, JOSEException;
    IntrospectResponse introspect(HttpServletRequest request) throws ParseException, JOSEException;
}
