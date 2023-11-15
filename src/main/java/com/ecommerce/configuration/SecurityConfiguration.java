package com.ecommerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
            .authorizeHttpRequests(auth->{
                auth.requestMatchers(HttpMethod.GET, "api/v1/index2").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/v1/productos").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/producto/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/apu/v1/pedido").permitAll()
                .anyRequest().authenticated(); // Endpoints permitidos (no se aplica seguridad), y el resto si debe estar autenticado
            })
            .formLogin(form->{
                form.permitAll();
            })
            .build();
    }
}
