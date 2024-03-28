package org.example.config.securityconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public UserDetailsService userDetailService(){
//        return new UserDetailService();
//    }
//
//    @Bean
//    public HttpSecurity securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    private AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService();
//        daoAuthenticationProvider.setPasswordEncoder();
//    }
//

}
