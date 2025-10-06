package com.dev.spring_security_basics.controller;

import com.dev.spring_security_basics.config.TokenConfig;
import com.dev.spring_security_basics.dto.request.LoginRequest;
import com.dev.spring_security_basics.dto.request.RegisterUserRequest;
import com.dev.spring_security_basics.dto.response.LoginResponse;
import com.dev.spring_security_basics.dto.response.RegisterUserResponse;
import com.dev.spring_security_basics.entity.User;
import com.dev.spring_security_basics.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        // Cria um objeto de autenticação contendo o email e a senha fornecidos pelo usuário.
        // O Spring Security usará esses dados internamente para validar a senha.
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        // Chama o AuthenticationManager para autenticar o usuário.
        // Internamente, ele usa o AuthConfig (UserDetailsService) para buscar o usuário pelo email
        // e um AuthenticationProvider para validar a senha com o PasswordEncoder configurado (ex: BCrypt).
        // Se a autenticação falhar, uma exceção é lançada; se for bem-sucedida, retorna um Authentication válido.
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        // O objeto Authentication contém o usuário autenticado como principal.
        // No  caso, a entidade User implementa UserDetails, então podemos fazer o cast.
        User user = (User) authentication.getPrincipal();

        // Gera o token JWT para o usuário autenticado.
        String token = tokenConfig.generateToken(user);

        // Retorna o token no corpo da resposta.
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        User newUser = new User();

        newUser.setEmail(request.email());
        newUser.setName(request.name());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(newUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterUserResponse(newUser.getName(), newUser.getEmail()));
    }


}
