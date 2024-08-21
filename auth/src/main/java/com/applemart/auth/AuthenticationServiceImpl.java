package com.applemart.auth;

import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.registration.RegistrationService;
import com.applemart.auth.user.User;
import com.applemart.auth.user.UserRepository;
import com.applemart.auth.user.UserService;
import com.applemart.auth.utils.JWTUtil;
import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request) throws ParseException, JOSEException {
        String identifier = request.getAuthIdentifier();

        User user = userService.isUserExist(identifier)
                .orElseThrow(() -> new BadCredentialsException("Account does not exist"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new BadCredentialsException("Password is incorrect");
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
