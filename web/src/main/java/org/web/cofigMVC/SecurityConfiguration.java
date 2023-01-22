package org.web.cofigMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.web.authentication.MyDBAuthenticationService;

import static org.springframework.security.config.Customizer.withDefaults;

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
    @Order(1)
    public SecurityFilterChain apiLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/addUser")
                .authorizeHttpRequests(authorize->authorize
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/user/allUsers").hasAnyRole("Admin")
                        .requestMatchers("/").permitAll()
                       // .requestMatchers("/user/**","/topic/**","/post/**","/welcome").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin()
                .successForwardUrl("/loginS");
        return http.build();
    }
}
