package com.applemart.backend.auth;

import com.applemart.backend.user.User;
import com.applemart.backend.user.UserRepository;
import com.applemart.backend.exception.ResourceNotFoundException;
import com.applemart.backend.utils.JWTUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;


    @Override
    public AuthenticationResponse login(AuthenticationRequest request) throws ParseException, JOSEException {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new BadCredentialsException("Username or password is incorrect");
        }

        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));

        String token = jwtUtil.issueToken(user.getUsername(), roles);

        jwtUtil.verifyToken(token);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
