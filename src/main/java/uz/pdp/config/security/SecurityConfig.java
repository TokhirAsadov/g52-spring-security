package uz.pdp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticatedFailHandler customAuthenticatedFailHandler;

    private final String[] WHITE_URL = {
            "/css/**",
            "/auth/login",
            "/auth/register"
    };

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticatedFailHandler customAuthenticatedFailHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticatedFailHandler = customAuthenticatedFailHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.userDetailsService(customUserDetailsService);

        http.authorizeHttpRequests()
                .requestMatchers(WHITE_URL).permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest()
                .authenticated(); //.fullyAuthenticated();


        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .usernameParameter("uname")
                .passwordParameter("pswd")
                .defaultSuccessUrl("/text", true)
                .failureHandler(customAuthenticatedFailHandler);

        http.logout()
                .logoutUrl("/auth/logout")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout","POST"));

        http.rememberMe()
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("rem-me")
                .tokenValiditySeconds(24 * 60 * 60)
                .key("secret_key")
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    /*@Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
                .username("ali")
//                .password(encoder.encode("123"))
                .password("123")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
//                .password(encoder.encode("123"))
                .password("123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/


}
