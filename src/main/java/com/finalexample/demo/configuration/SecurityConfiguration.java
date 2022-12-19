package com.finalexample.demo.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_ENDPOINTS={"/","/home","/contact","/about-us","/cars/**","/upload-image/**"};
    private static final String[] ADMIN_ENDPOINTS={"/admin", "/admin/**"};
    private static final String[] USER_ENDPOINTS={"/user", "/user/**"};



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .antMatchers(ADMIN_ENDPOINTS).hasAnyAuthority("ADMIN")
                .antMatchers(USER_ENDPOINTS).hasAnyAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .csrf().disable().cors()
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user = User
                .builder()
                .username("user")
                .password("user")
                .authorities("USER")
                .build();

        UserDetails admin = User
                .builder()
                .username("admin")
                .password("admin")
                .authorities("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();//asnjelloj algoritmi per hashim
    }
}
