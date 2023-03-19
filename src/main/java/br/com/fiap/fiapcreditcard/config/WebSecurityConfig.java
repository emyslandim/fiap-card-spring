package br.com.fiap.fiapcreditcard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("rws").password("{noop}FiapCreditCard").roles("RWS", "STUDENT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "**").hasRole("RWS")
                .antMatchers(HttpMethod.POST, "**").hasRole("RWS")
                .antMatchers(HttpMethod.PUT, "**").hasRole("RWS")
                .antMatchers(HttpMethod.PATCH, "**").hasRole("RWS")
                .antMatchers(HttpMethod.DELETE, "*").hasRole("RWS")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
