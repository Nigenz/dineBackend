package com.dine.restaurant.SecurityConfigs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig{
	
	private static final String [] WHITE_LIST_URLS = {
			"/register",
			"/save",
			"/verifyRegistration",
			"/resendVerificationToken",
			"/resetPassword",
			"/savePassword",
			"/changePassword"
	};
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(WHITE_LIST_URLS).permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .oauth2Login(oauth2login ->
                        oauth2login.loginPage("/oauth2/authorization/api-client-oidc"))
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }
    
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    http
//	    .cors()
//	    .and()
//	    .csrf()
//	    .disable()
//	    .authorizeHttpRequests()
//	    .antMatchers(WHITE_LIST_URLS).permitAll();
//	      
//	    return http.build();
//	}
//	
	
//	@Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
//    }
 
}

