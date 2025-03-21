package com.example.taskmanage.config;

import com.example.taskmanage.config.filter.CustomAuthenticationProvider;
import com.example.taskmanage.config.filter.JwtTokenAuthenticationFilter;
import com.example.taskmanage.config.filter.JwtUsernamePasswordAuthenticationFilter;
import com.example.taskmanage.exception.CustomAccessDeniedHandler;
import com.example.taskmanage.jwt.JwtConfig;
import com.example.taskmanage.jwt.JwtService;
import com.example.taskmanage.service.RefreshTokenService;
import com.example.taskmanage.service.security.UserDetailsServiceCustom;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceCustom();
    }

//    @Autowired
//    public void configGlobal(final AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(customAuthenticationProvider);
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        builder.authenticationProvider(customAuthenticationProvider);

        AuthenticationManager manager = builder.build();

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .requestMatchers("/account/**").permitAll()
                .requestMatchers("/guest/**").permitAll()
                .requestMatchers("/refreshToken/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user/**", "/task/**").hasAnyAuthority("USER", "EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .authenticationManager(manager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(
                        new JwtUsernamePasswordAuthenticationFilter(manager, jwtConfig, jwtService, refreshTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(
                        new JwtTokenAuthenticationFilter(jwtConfig, jwtService),
                        UsernamePasswordAuthenticationFilter.class)
        ;


        return http.build();
    }
}
