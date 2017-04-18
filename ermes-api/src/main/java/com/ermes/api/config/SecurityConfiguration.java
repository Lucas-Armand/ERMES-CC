package com.ermes.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private DataSource dataSource;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.authorities-query}")
	private String authoritiesQuery;

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{		
		http.csrf().disable();
		http.httpBasic();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll();
		http.authorizeRequests().antMatchers("/users/**").hasAuthority("ROLE_ADMIN");
		http.authorizeRequests().anyRequest().hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
	    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication()
		.usersByUsernameQuery(usersQuery)
		.authoritiesByUsernameQuery(authoritiesQuery)
		.dataSource(dataSource)
		.passwordEncoder(bCryptPasswordEncoder);
	}
}
