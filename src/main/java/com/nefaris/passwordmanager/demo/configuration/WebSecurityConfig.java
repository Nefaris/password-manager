package com.nefaris.passwordmanager.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // todo add password encoding (noop encoder for tests)
        // return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(User.builder()
                .username("admin")
                .password(getPasswordEncoder().encode("admin"))
                .roles("ADMIN", "USER")
        );

        auth.inMemoryAuthentication().withUser(User.builder()
                .username("user")
                .password(getPasswordEncoder().encode("user"))
                .roles("USER")
        );
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
                .formLogin().loginPage("/login").failureUrl("/loginError").defaultSuccessUrl("/dashboard")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

}
