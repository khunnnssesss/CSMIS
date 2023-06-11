package com.dat.csmis.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;




@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetail();
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
				   .authorizeHttpRequests()
	               .antMatchers("/login","/resources/**", "/static/**", "/login/**", "/forgetPassword", "/doForgetPassword","/resetPassword","/doResetPassword")
	               .permitAll()
				   .antMatchers("/admin/**")
				   .hasRole("ADMIN")
				   .antMatchers("/user/**")
				   .hasRole("USER")
				   .anyRequest()
				   .authenticated()
				   .and()
				   .formLogin()
				   .loginPage("/login")
				   .defaultSuccessUrl("/", true)
				   .and()
				   .logout()
				   .logoutUrl("/logout")
				   .logoutSuccessUrl("/login")
				   .clearAuthentication(true)
				   .invalidateHttpSession(true)
				   .deleteCookies("JSESSIONID")
				   .permitAll()
				   .and()
				   .rememberMe()
				   .key("1001010110110");
		
		return http.build();	
	}
	
	@Bean
	public SpringSecurityDialect dia() {
		return new SpringSecurityDialect();
	}

}