package com.course.cars.api.security;

import com.course.cars.api.security.jwt.JwtAuthenticationFilter;
import com.course.cars.api.security.jwt.JwtAuthorizationFilter;
import com.course.cars.api.security.jwt.handler.AccessDeniedHandler;
import com.course.cars.api.security.jwt.handler.UnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationManager();

        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.GET, "/api/v1/login")
                        .permitAll()
                        .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
//                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilter(new JwtAuthenticationFilter(authManager))
                .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService))

                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();

//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/v1/login").permitAll()
//                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .addFilter(new JwtAuthenticationFilter(authManager))
//                .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService))
//                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler)
//                .authenticationEntryPoint(unauthorizedHandler)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    @Bean
//    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("phelipe")
//                .password(bCryptPasswordEncoder.encode("13031882"))
//                .roles("PHELIPE", "ADMIN")
//                .build());
//        manager.createUser(User.withUsername("admin")
//                .password(bCryptPasswordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build());
//        return manager;
//    }
}
