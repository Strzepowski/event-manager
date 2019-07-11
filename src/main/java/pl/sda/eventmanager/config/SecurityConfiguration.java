package pl.sda.eventmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    //TODO there is more than one bean?

    public SecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home", "/register", "/h2db")
                .permitAll()
                .and().formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
                .permitAll()
                .and().logout()
                .permitAll();



        //H2
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderConfiguration.passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}