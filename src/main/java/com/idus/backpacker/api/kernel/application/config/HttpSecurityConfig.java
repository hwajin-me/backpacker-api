package com.idus.backpacker.api.kernel.application.config;

import com.idus.backpacker.api.kernel.application.config.filter.HttpAuthJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
    private final HttpAuthJwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(httpSecurityAuthenticationEntryPoint())
                .accessDeniedHandler(httpSecurityAccessDeniedEntryPoint());

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs")
                .permitAll()
                .antMatchers("/users", "/users/**")
                .permitAll()
                .antMatchers("/orders", "/orders/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth")
                .permitAll()
                .antMatchers("/auth/-/refresh")
                .authenticated()
                .antMatchers(HttpMethod.DELETE, "/auth")
                .authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .headers()
                .cacheControl();
    }

    @Bean
    HttpSecurityAuthenticationEntryPoint httpSecurityAuthenticationEntryPoint() {
        return new HttpSecurityAuthenticationEntryPoint();
    }

    @Bean
    HttpSecurityAccessDeniedEntryPoint httpSecurityAccessDeniedEntryPoint() {
        return new HttpSecurityAccessDeniedEntryPoint();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
