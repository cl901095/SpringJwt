package com.jwt.SpringJwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
   private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


//       http.csrf(customizer -> customizer.disable());
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//        http.formLogin(Customizer.withDefaults()); // for UI
//        http.httpBasic(Customizer.withDefaults()); // for post man
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

      return   http.csrf(customizer ->customizer.disable())
                .authorizeHttpRequests(request -> request
                .requestMatchers("register","login")
                .permitAll()
                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


//             return http.build();

        // by using of above lambada expression we skip the below code
//        Customizer<CsrfConfigurer<HttpSecurity>> custCsrf=new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//                customizer.disable();
//            }
//        };
//        http.csrf(custCsrf);
//

        }
        @Bean
        public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
            provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
            provider.setUserDetailsService(userDetailsService);
            return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
             return config.getAuthenticationManager();
        }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1=
//                User.withDefaultPasswordEncoder()
//                        .username("kiran")
//                        .password("kiran")
//                        .roles("USER")
//                        .build();
//
//
//        UserDetails user2= User
//                .withDefaultPasswordEncoder()
//                .username("chandu")
//                .password("chandu")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


}
