package com.ssg.starroadadmin.global.config;

import com.ssg.starroadadmin.user.service.impl.ManagerDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final ManagerDetailsServiceImpl managerDetailsService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .requestMatchers("/login", "/signup", "/user").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/articles")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
                .anyRequest().permitAll() // 모든 요청을 허용하여 로그인 기능을 비활성화
                .and()
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(managerDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return auth.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
