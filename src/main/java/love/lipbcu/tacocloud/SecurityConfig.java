package love.lipbcu.tacocloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add {noop} before password, otherwise ``There is no PasswordEncoder mapped for the id "null"`` will occur.
        // see https://github.com/spring-projects/spring-security/issues/5086
        auth.inMemoryAuthentication()
                .withUser("buzz")
                .password("{noop}infinity")
                .authorities("ROLE_USER")
            .and()
                .withUser("woody")
                .password("{noop}bullseye")
                .authorities("ROLE_USER");
    }
}
