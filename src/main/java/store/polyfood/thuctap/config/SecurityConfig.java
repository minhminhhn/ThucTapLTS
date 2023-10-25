package store.polyfood.thuctap.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import store.polyfood.thuctap.services.AccountService;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AccountService accountService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().httpBasic()
//                .formLogin().and()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/register",
                        "/api/auth/login",
                        "/forgot_password",
                        "/reset_password",
                        "/api/product/getall",
                        "/api/product/getbyid",
                        "/api/product/getfinalfrice",
                        "/api/product/getproductimage",
                        "/api/product/numberofview",
                        "/api/product/relatedproducts",
                        "/api/payment/create_payment",
                                "/api/payment/vnpay_return")
                .permitAll()
                .requestMatchers("/api/account/getaccount").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/api/account/**",
                        "/api/product/**",
                        "/api/user/getall",
                        "/api/user/findbyname",
                        "/api/orders/update",
                        "/api/orders/getall",
                        "/api/orders/updatestatusorder",
                        "/api/orders/update").hasAuthority("ADMIN")
                .requestMatchers("/api/user/create",
                        "/api/user/update",
                        "/api/orders/getorders",
                        "/api/orders/create",
                        "/api/orders/getbyid").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(userIdAuthorizationMiddleware, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
