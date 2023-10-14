package ru.kotikov.blog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .formLogin()
                .defaultSuccessUrl("/", true)
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/", "/user/registration").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/post").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/user").authenticated()
                                .requestMatchers("/api/v1/user**", "/user/all").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .logout()
                .logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
