package com.javavier.app.spring_boot_data_jpa;

import com.javavier.app.spring_boot_data_jpa.auth.handler.LoginSuccesHandler;
import com.javavier.app.spring_boot_data_jpa.models.service.JpaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableGlobalAuthentication
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private LoginSuccesHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailService userDetailService;



    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    /* *
    @Bean
    public UserDetailsService userDetailsService()throws Exception{

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN","USER")
                .build());

        return manager;

    }
    * */


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                        (authz) -> authz
                                //.requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
                                //.requestMatchers("/ver/**").hasAnyRole("USER")
                                //.requestMatchers("/uploads/**").hasAnyRole("USER")
                                //.requestMatchers("/form/**").hasAnyRole("ADMIN")
                                //.requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
                                //.requestMatchers("/factura/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .successHandler(successHandler)
                        .loginPage("/login").permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/error_403"))
                .logout(logout -> logout.permitAll());

        return http.build();
    }

}
