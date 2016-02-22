package com.swansoftwaresolutions.jirareport.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author Vladimir Martynyuk
 */

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.swansoftwaresolutions.jirareport.core.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private SecurityUserDetailsService securityUserDetailsService;
//
//    @Autowired
//    AuthSuccess authSuccess;
//
//    @Autowired
//    AuthFailure authFailure;
//
//    @Autowired
//    LogoutSuccess logoutSuccess;
//
//    @Autowired
//    AccessDeniedHandler accessDeniedHandler;
//
//
////    public SecurityConfig() {
////        super(true);
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint())
//                .and()
//                .csrf().disable()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).sessionFixation().migrateSession()
//                .and()
//                    .anonymous()
//                .and()
//                .authorizeRequests()
//                    .antMatchers("/rest/auth/**").anonymous()
//                    .antMatchers("/rest/main/**").anonymous()
//                    .antMatchers("/rest/**").hasRole(UserType.USER.toString())
//                .and()
//                .formLogin()
//                    .loginPage("/rest/auth/login")
//                    .usernameParameter("login")
//                    .passwordParameter("password")
//                    .successHandler(authSuccess)
//                    .failureHandler(authFailure)
//                .and()
//                    .logout()
//                         .clearAuthentication(true)
//                         .deleteCookies("JSESSIONID")
//                         .logoutRequestMatcher(new AntPathRequestMatcher("/rest/auth/logout"))
//                         .logoutSuccessHandler(logoutSuccess);
//    }
//
//    @Bean
//    RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return securityUserDetailsService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
