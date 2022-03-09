package io.github.wyllian.config;

import io.github.wyllian.security.jwt.JwtAuthFilter;
import io.github.wyllian.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.wyllian.service.impl.UsuarioServiceImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;
    
    @Autowired
    private JwtService jwtService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioServiceImpl);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {       
        super.configure(auth);
        // auth.inMemoryAuthentication()
        //     .passwordEncoder(passwordEncoder())
        //     .withUser("admin")
        //     .password(passwordEncoder().encode("password"))
        //     .roles("USER");
       //auth.userDetailsService(usuarioServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {        
        //super.configure(http);
//        http
//            .csrf().disable()
//            .authorizeRequests()
//            .antMatchers("/api/clientes/**")
//                .hasAnyRole("USER", "ADMIN")
//            .antMatchers("/api/produtos/**")
//                .hasRole("ADMIN")
//            .antMatchers("api/pedidos/**")
//                .hasRole("USER")
//            .antMatchers(HttpMethod.POST, "/api/usuarios/**")
//            .permitAll()    
//            .anyRequest().authenticated()
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
            //.httpBasic();
            //.formLogin();

        http
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/books").hasRole("USER")
            .antMatchers("/books2").hasRole("ADMIN")
            .and()
            .csrf().disable()
            .headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        web.ignoring().antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
        );
    }
}