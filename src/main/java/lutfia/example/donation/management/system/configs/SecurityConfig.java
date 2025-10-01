package lutfia.example.donation.management.system.configs;

import lombok.AllArgsConstructor;
import lutfia.example.donation.management.system.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

                http
                        .csrf(c-> c.disable())
                        .sessionManagement(s->s.sessionCreationPolicy(STATELESS))
                        .authorizeHttpRequests(request->request
                                .requestMatchers("/api/v1/user",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/swagger-ui/**",
                                        "/v2/api-docs",
                                        "/v2/api-docs/**",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**"
                                        )
                                        .permitAll()
                                        .requestMatchers("/api/v1/login")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                                );
                return http.build();
    }
    @Bean
    public AuthenticationProvider auth (){
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
    throws Exception{
        return configuration.getAuthenticationManager();
    }


}
