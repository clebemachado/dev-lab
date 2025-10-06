package com.dev.spring_security_basics.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Desativa o CSRF, que protege formulários de envio de requisições maliciosas.
        // Em APIs REST stateless com JWT, geralmente não é necessário.
        http.csrf(csrf -> csrf.disable());

        // Desativa o CORS. Isso bloqueia a checagem de origem pelo Spring Security.
        // Use com cuidado; normalmente o CORS é configurado separadamente quando necessário.
        http.cors(cors -> cors.disable());

        // Configura o servidor para não criar sessão HTTP.
        // APIs REST stateless devem ser stateless, usando autenticação via token (JWT) em vez de sessão.
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Configura autorização das requisições
        http.authorizeHttpRequests(authorize -> authorize
                // Permite que páginas de erro sejam acessadas sem autenticação
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                // Permite login sem autenticação
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                // Permite registro de usuário sem autenticação
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                // Qualquer outra requisição precisa estar autenticada
                .anyRequest().authenticated()
        );

        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @DependsOn("securityFilterChain")
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
