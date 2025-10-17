
package com.javanauta.agendadortarefas.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Instâncias de com.javanauta.agendadortarefas.infrastructure.security.JwtUtil e UserDetailsService injetadas pelo Spring
    private final com.javanauta.agendadortarefas.infrastructure.security.JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    // Construtor para injeção de dependências de com.javanauta.agendadortarefas.infrastructure.security.JwtUtil e UserDetailsService
    @Autowired
    public SecurityConfig(com.javanauta.agendadortarefas.infrastructure.security.JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Configuração do filtro de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cria uma instância do com.javanauta.agendadortarefas.infrastructure.security.JwtRequestFilter com com.javanauta.agendadortarefas.infrastructure.security.JwtUtil e UserDetailsService
        com.javanauta.agendadortarefas.infrastructure.security.JwtRequestFilter jwtRequestFilter = new com.javanauta.agendadortarefas.infrastructure.security.JwtRequestFilter(jwtUtil, userDetailsService);

        http
                .csrf(AbstractHttpConfigurer::disable) // Desativa proteção CSRF para APIs REST (não aplicável a APIs que não mantêm estado)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated() // Requer autenticação para todas as outras requisições
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura a política de sessão como stateless (sem sessão)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT antes do filtro de autenticação padrão

        // Retorna a configuração do filtro de segurança construída
        return http.build();
    }


}
