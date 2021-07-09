package rest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rest.config.handler.AuthSuccessHandler;
import rest.service.UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private AuthSuccessHandler authSuccessHandler;

    public WebSecurityConfig(UserService userService, AuthSuccessHandler authSuccessHandler) {
        this.userService = userService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                //.permitAll()
                .successHandler(authSuccessHandler)
                //.failureHandler()
                .usernameParameter("j_email")
                .passwordParameter("j_password");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout")
                .permitAll()
                .and()
                .csrf().disable();

        http
                .authorizeRequests().antMatchers("/").anonymous()
                .and()
                .authorizeRequests().antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN");

    }

    // !!! Не забудь подключть!!!
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
