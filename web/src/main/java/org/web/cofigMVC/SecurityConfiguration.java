package org.web.cofigMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.web.authentication.MyDBAuthenticationService;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"org.web"})
public class SecurityConfiguration {
    @Autowired
    MyDBAuthenticationService userDetailService;

    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/user/users","/topic/create").hasAnyRole("Admin")
                        .requestMatchers("/user/**","/topic/**","/post/**","/welcome").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin()
                .successForwardUrl("/loginS");
        return http.build();
    }
}
