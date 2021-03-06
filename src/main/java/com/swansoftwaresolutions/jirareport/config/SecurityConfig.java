package com.swansoftwaresolutions.jirareport.config;

import com.swansoftwaresolutions.jirareport.config.security.*;
import com.swansoftwaresolutions.jirareport.domain.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Vladimir Martynyuk
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled = true)
@ComponentScan(basePackages = {"com.swansoftwaresolutions.jirareport.config.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AuthSuccess authSuccess;

    @Autowired
    private AuthFailure authFailure;

    @Autowired
    private LogoutSuccess logoutSuccess;

    @Autowired
    private AccessDenied accessDenied;

    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/rest/auth/**").permitAll()
                .antMatchers("/rest/v1/system_users").hasRole("ADMIN")
                .antMatchers("/rest/**").hasRole("MANAGER")
//                    .antMatchers("/**").anonymous()
                    .and()

                .formLogin()
                    .loginPage("/rest/auth/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authSuccess)
                    .failureHandler(authFailure)
                    .and()

                .logout()
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/rest/auth/logout"))
                    .logoutSuccessHandler(logoutSuccess)
                    .and()

                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                .accessDeniedHandler(accessDenied)
                    .and()
                .csrf().disable();
    }

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
