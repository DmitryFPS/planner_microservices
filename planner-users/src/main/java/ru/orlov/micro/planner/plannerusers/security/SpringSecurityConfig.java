package ru.orlov.micro.planner.plannerusers.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import ru.orlov.micro.planner.utils.convert.KCRoleConverter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KCRoleConverter());

        http
//                .authorizeRequests()
//                .antMatchers("/api/test/login").permitAll()
//                .anyRequest().authenticated()

                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("admin") //все что связанно с (CRUD) над пользователями
                .antMatchers("/auth/*").hasRole("user") // все что связанно с аутентификацией пользователя
                .anyRequest()
                .authenticated()

                .and()

                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);

        return http.build();
    }
}