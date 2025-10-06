package com.dev.spring_security_basics.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenConfig tokenConfig;

    public SecurityFilter(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Pega o token enviado pelo cliente.
        String authorizedHeader = request.getHeader("Authorization");

        // Garante que a requisição tem token no formato correto.
        if (Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {
            // Remove o prefixo "Bearer " para usar apenas o token JWT.
            String token = authorizedHeader.substring("Bearer ".length());

            // Valida o token.
            Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
            if (optUser.isPresent()) {
                JWTUserData jwtUserData = optUser.get();
                // Cria o objeto de autenticação do Spring Security.
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                if (jwtUserData.roles() != null) {
                    authorities.addAll(jwtUserData.roles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList());
                }

                if (jwtUserData.operations() != null) {
                    authorities.addAll(jwtUserData.operations().stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList());
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtUserData, null, authorities);

                // Coloca a autenticação no contexto do Spring Security, permitindo que o usuário seja identificado nas próximas chamadas.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }


    }
}
