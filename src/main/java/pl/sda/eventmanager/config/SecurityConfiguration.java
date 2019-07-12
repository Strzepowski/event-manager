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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;
    private final DataSource dataSource;

    //TODO there is more than one bean?

    public SecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoderConfiguration passwordEncoderConfiguration, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        this.dataSource = dataSource;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT email, password FROM user WHERE email=?")
////                .authoritiesByUsernameQuery("SELECT email, role from user_role WHERE username=?")
//;
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/register", "/h2db/**")
                    .permitAll()
                    .anyRequest()
                        .fullyAuthenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureForwardUrl("/login")
                    .successForwardUrl("/")
                    .permitAll()
                .and()
                    .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                    .userDetailsService(userDetailsService);



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