package com.duoc.demsecurityone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos: solo información general, no datos personales.
                .requestMatchers(HttpMethod.GET, "/api/publico", "/api/saludo").permitAll()
                // Solo administradores pueden consultar la ruta administrativa.
                .requestMatchers(HttpMethod.GET, "/api/admin").hasRole("ADMIN")
                // Empleados o administradores pueden consultar la ruta interna.
                .requestMatchers(HttpMethod.GET, "/api/empleados").hasAnyRole("EMPLEADO", "ADMIN")
                // Cualquier usuario autenticado puede consultar su perfil.
                .requestMatchers(HttpMethod.GET, "/api/perfil").authenticated()
                // Política restrictiva: todo lo no declarado queda denegado.
                .anyRequest().denyAll()
            )
            // HTTP Basic se utiliza para esta API de demostración.
            // En producción debe usarse HTTPS y una estrategia adecuada al cliente.
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // Usa el delegating encoder de Spring Security en lugar de almacenar
        // contraseñas en texto plano.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // Usuarios de demostración almacenados en memoria.
        // No usar estas credenciales en producción.
        UserDetails cliente = User.withUsername("cliente")
            .password(encoder.encode("ClienteDemo-2026!"))
            .roles("CLIENTE")
            .build();

        UserDetails empleado = User.withUsername("empleado")
            .password(encoder.encode("EmpleadoDemo-2026!"))
            .roles("EMPLEADO")
            .build();

        UserDetails administrador = User.withUsername("admin")
            .password(encoder.encode("AdminDemo-2026!"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(cliente, empleado, administrador);
    }
}
