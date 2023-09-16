package com.br.alura.forum.infra.secutiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cursos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/topicos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/topicos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/topicos/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/topicos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/respostas").authenticated()
                        .requestMatchers(HttpMethod.POST, "/respostas/{id}?solucao").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/respostas/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/respostas/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/respostas/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/usuarios/todos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
