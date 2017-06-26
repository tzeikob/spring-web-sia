package com.tkb.demo.config;

import com.tkb.demo.model.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/a/**").hasRole(RoleType.ADMIN.getName())
                .antMatchers(HttpMethod.POST, "/a/**").hasRole(RoleType.ADMIN.getName())
                .antMatchers("/c/**").authenticated()
                .anyRequest().permitAll();
        
        //http.requiresChannel().antMatchers("/").requiresInsecure().and()
        //.requiresChannel().antMatchers("/persons").requiresInsecure().and()
        //.requiresChannel().antMatchers("/persons/**").requiresInsecure().and()
        //.requiresChannel().antMatchers(HttpMethod.GET, "/person/register").requiresSecure().and()
        //.requiresChannel().antMatchers(HttpMethod.POST, "/person/register").requiresSecure();

        http.formLogin().loginPage("/login")
                .and().rememberMe().tokenValiditySeconds(160).key("demo_key")
                .and().httpBasic();

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder(4));
    }
}
