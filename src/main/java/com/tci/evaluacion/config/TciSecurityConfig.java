package com.tci.evaluacion.config;

import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.security.JwtTokenFilter;
import com.tci.evaluacion.security.JwtTokenProvider;
import com.tci.evaluacion.security.TciAccessDeniedHandler;
import com.tci.evaluacion.security.TciAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class TciSecurityConfig {

  AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;

  private final String API_LOGIN = TciConstants.API_VERSION +
          TciConstants.RESOURCE_USERS + TciConstants.RESOURCE_LOGIN;

  private final String API_EMPRESA_REGISTRO = TciConstants.API_VERSION +
          TciConstants.RESOURCE_EMPRESA + TciConstants.RESOURCE_EMPRESA_REGISTRO;

  private final String API_EMPRESA_LISTADO = TciConstants.API_VERSION +
          TciConstants.RESOURCE_EMPRESA + TciConstants.RESOURCE_EMPRESA_LISTADO;



  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService);
    authenticationManager = authenticationManagerBuilder.build();

    http.csrf().disable().cors().disable().authorizeHttpRequests()
        .antMatchers(API_LOGIN).permitAll()
        .antMatchers(API_EMPRESA_REGISTRO).hasAuthority(TciConstants.USER_ROLE)
        .antMatchers(API_EMPRESA_LISTADO).hasAuthority(TciConstants.USER_ROLE)
        .anyRequest().authenticated()
        .and()
        .authenticationManager(authenticationManager)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint());
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint(){
    return new TciAuthenticationEntryPoint();
  }

  @Bean
  AccessDeniedHandler accessDeniedHandler(){
    return new TciAccessDeniedHandler();
  }

  @Bean
  public JwtTokenFilter authenticationJwtTokenFilter() {
    return new JwtTokenFilter(jwtTokenProvider);
  }
}
