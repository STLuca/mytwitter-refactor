package com.example.Config;

import com.example.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${spring.data.rest.basePath}")
    private String apiBasePath;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("adminpassword123")).roles("ADMIN");
        auth
                .userDetailsService(new CustomUserService(userRepository))
                .passwordEncoder(passwordEncoder);

    }

    //instead of hard typing 'api' here, should read the value from properties
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/api").permitAll()
                //USERS
                .antMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/users/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/users/*").authenticated()
                .antMatchers("/api/users/**").denyAll()
                //TWEETS
                .antMatchers(HttpMethod.GET, "/api/tweets/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/tweets").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/tweets/*").authenticated()
                .antMatchers("/api/tweets/**").denyAll()
                //FOLLOWS
                .antMatchers(HttpMethod.GET, "/api/follows/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/follows").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/follows/*").authenticated()
                .antMatchers("/api/follows/**").denyAll()
                //LIKES
                .antMatchers(HttpMethod.GET, "/api/likes/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/likes").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/likes/*").authenticated()
                .antMatchers("/api/likes/**").denyAll()

                .antMatchers("/actuator/**").hasRole("ADMIN")
                .antMatchers("/**").authenticated()
                .and().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .and().logout().logoutSuccessUrl("/")
                .and().csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/font/**", "/img/**", "/scss/**",
                        "/v2/api-docs", "/configuration/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/csrf");
    }


    @Bean
    public EvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

}