package com.applemart.backend.auth;

import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request) throws ParseException, JOSEException;
}
