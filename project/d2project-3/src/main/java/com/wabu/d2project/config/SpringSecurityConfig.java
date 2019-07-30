package com.wabu.d2project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/test/generateTestCases");
		web.ignoring().antMatchers("/test");
		web.ignoring().antMatchers("/register");
		web.ignoring().antMatchers("/create");
		web.ignoring().antMatchers("/static/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.authorizeRequests()
			.antMatchers("/**").authenticated();
		http
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProcess")
       		.defaultSuccessUrl("/timeline", true) 
			.usernameParameter("id") 
			.passwordParameter("password")
			.permitAll();
		http
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.permitAll();
		http.cors().and();
		http.csrf().disable();
	}
	
	@Configuration
	public static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter
	{
		@Autowired
		UserDetailsService userDetailsService;
		
		@Bean
		PasswordEncoder passwordEncoder()
		{
			return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}
		
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception
		{
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
	}

}
