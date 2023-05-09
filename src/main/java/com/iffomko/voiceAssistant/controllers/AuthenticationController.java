package com.iffomko.voiceAssistant.controllers;

import com.iffomko.voiceAssistant.controllers.data.AuthenticationRequestDTO;
import com.iffomko.voiceAssistant.controllers.data.AuthenticationResponseDTO;
import com.iffomko.voiceAssistant.controllers.data.RegistrationRequestDTO;
import com.iffomko.voiceAssistant.controllers.data.RegistrationResponseDTO;
import com.iffomko.voiceAssistant.controllers.errors.AuthenticationError;
import com.iffomko.voiceAssistant.db.entities.Role;
import com.iffomko.voiceAssistant.db.entities.User;
import com.iffomko.voiceAssistant.db.services.dao.UserDao;
import com.iffomko.voiceAssistant.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(
            @Qualifier("authenticationManager") AuthenticationManager authenticationManager,
            UserDao userDao,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO body) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword())
            );

            User user = userDao.findUserByUsername(body.getUsername());
            String jwtToken = jwtTokenProvider.createToken(
                    user.getUsername(),
                    user.getRole()
            );

            return ResponseEntity.ok(new AuthenticationResponseDTO(user.getUsername(), jwtToken, null));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AuthenticationResponseDTO(
                            null,
                            null,
                                new AuthenticationError("Вы не вошли в систему")
                    )
            );
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO body) {
        System.out.println(body);

        User user = userDao.findUserByUsername(body.getUsername());

        if (user != null) {
            return ResponseEntity.badRequest().body(new RegistrationResponseDTO(
                    HttpStatus.BAD_REQUEST.value(),
                    "Вы уже зарегистрированы в системе"
            ));
        }

        user = new User();
        user.setUsername(body.getUsername());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setName(body.getName());
        user.setSurname(body.getSurname());
        user.setRole(Role.USER);

        userDao.addUser(user);

        return ResponseEntity.ok(
                new RegistrationResponseDTO(HttpStatus.OK.value(), "Вы были успешно зарегистрированы")
        );
    }
}
