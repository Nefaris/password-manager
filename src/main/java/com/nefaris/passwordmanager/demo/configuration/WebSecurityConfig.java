package com.nefaris.passwordmanager.demo.configuration;

import com.nefaris.passwordmanager.demo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService myUserDetailsService;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.myUserDetailsService = customUserDetailsService;
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()

                .antMatchers("/dashboard").authenticated()
                .antMatchers("/dashboard").hasRole("USER")

                .antMatchers("/swagger-ui.html").authenticated()
                .antMatchers("/swagger-ui.html").hasRole("ADMIN")

                .and()
                .formLogin().loginPage("/login").failureUrl("/login").defaultSuccessUrl("/dashboard")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

}
