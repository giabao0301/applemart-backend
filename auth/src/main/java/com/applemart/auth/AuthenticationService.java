package com.applemart.auth;

import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request) throws ParseException, JOSEException;
    IntrospectResponse introspect(IntrospectRequest token) throws ParseException, JOSEException;
}
