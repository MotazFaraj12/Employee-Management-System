package com.example.EmployeeManagement.security;

import com.example.EmployeeManagement.model.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    //.sessionManagement() means configuration the user sessions
    //.authorizeHttpRequests() means only allowing the path with /auth/** while requiring authentication for the others
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/Employees/auth/**","/Employees/{id}/Attendance/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/Payments").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Employees").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Employees/{email}").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Employees/Projects").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Payments/{year}/{month}").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Payments/Employees/{id}").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Employees/leaves/pending").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Employees/{email}/Projects").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/Employees/Leaves/{employee}").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/Employees/Leave/Request").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/Employees/Leave/{LeaveID}/Status").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/Employees/{email}/Projects/{id}").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/Employees/{email}/Departments/{id}").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/Employees/{email}/Role").hasAuthority(Roles.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/Employees/{email}").hasAuthority(Roles.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//STATELESS means that any session related data are not stored on the server side
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);//providing a custom filter in addition to the UsernamePasswordAuthenticationFilter filter
        return http.build();
    }
}