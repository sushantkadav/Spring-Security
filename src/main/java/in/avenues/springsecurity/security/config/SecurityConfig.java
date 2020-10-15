package in.avenues.springsecurity.security.config;

import in.avenues.springsecurity.security.filters.TokenAuthenticationFilter;
import in.avenues.springsecurity.security.filters.UsernamePasswordAuthenticationFilter;
import in.avenues.springsecurity.security.providers.OtpAuthenticationProvider;
import in.avenues.springsecurity.security.providers.TokenAuthenticationProvider;
import in.avenues.springsecurity.security.providers.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    public SecurityConfig(@Lazy UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter, @Lazy TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(usernamePasswordAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(tokenAuthenticationFilter, BasicAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public InitializingBean initializingBean() {
        return () -> {
            SecurityContextHolder.setStrategyName(
                    SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
            );
        };
    }
}
