package com.chw.shopping.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain maSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/ma/**")
                .authorizeRequests(auth -> auth
                    .antMatchers("/ma/login", "/ma/logout", "/ma/login/**").permitAll()
                    .antMatchers("/ma/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .formLogin(login -> login
                    .loginPage("/ma/login/login")
                    .loginProcessingUrl("/ma/login")
                    .defaultSuccessUrl("/ma/main/main", true)
                    .failureUrl("/ma/login/login?error=true")
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/ma/logout")
                    .logoutSuccessUrl("/ma/login/login")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
                )
                .headers(headers -> headers
                    .cacheControl().disable()
                )
                .sessionManagement(session -> session
                    .sessionFixation().changeSessionId()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }

    @Bean
    public SecurityFilterChain ftSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/ft/**")
                .authorizeRequests(auth -> auth
                        .antMatchers("/ft/login/**", "/ft/signup/**", "/ft/login", "/ft/logout","/ft/main/main").permitAll()
                        .antMatchers("/ft/**").hasAnyRole("USER", "BIZ", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/ft/login/login")
                        .loginProcessingUrl("/ft/login")
                        .defaultSuccessUrl("/ft/main/main", true)
                        .failureUrl("/ft/login/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/ft/logout")
                        .logoutSuccessUrl("/ft/main/main")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .headers(headers -> headers
                        .cacheControl().disable()
                )
                .sessionManagement(session -> session
                        .sessionFixation().changeSessionId()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }

    @Bean
    public SecurityFilterChain fileSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/file/**")
                .authorizeRequests(auth -> auth
                        .antMatchers("/file/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
