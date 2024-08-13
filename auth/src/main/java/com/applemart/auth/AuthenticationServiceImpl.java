package com.applemart.auth;

import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.registration.RegistrationService;
import com.applemart.auth.user.User;
import com.applemart.auth.user.UserRepository;
import com.applemart.auth.utils.JWTUtil;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

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
