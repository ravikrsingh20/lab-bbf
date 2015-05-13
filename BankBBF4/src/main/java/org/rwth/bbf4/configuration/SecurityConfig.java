package org.rwth.bbf4.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	DataSource dataSource;
	@Autowired
	BBF4AuthenticationSuccessHandler successHandler;
 
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
 
		auth.jdbcAuthentication().dataSource(dataSource)
		.passwordEncoder(passwordEncoder())
		.usersByUsernameQuery("select UA_ACNT_ID username ,UA_ONLN_PIN password, UA_ENABLED enabled from USER_ACNT where UA_ACNT_ID=?")
		.authoritiesByUsernameQuery("select AR_ACNT_ID username, AR_RL_NM role from ACNT_ROLE where AR_ACNT_ID=?");
	}	
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	  http.authorizeRequests()		  		
		.antMatchers("/bbf4/","/bbf4/atm/**","/bbf4/login/**","/bbf4/register/**","/resources/**").permitAll()	
		.antMatchers("/bbf4/emp/**").access("hasRole('EMPL') or hasRole('ADMN')")
		.antMatchers("/bbf4/onln/**").access("hasRole('CUST')")	
		.anyRequest().authenticated()
		.and()
		  .formLogin().loginPage("/login").successHandler(successHandler).failureUrl("/login?error")
		  .usernameParameter("username").passwordParameter("password")
		.and()
		  .logout().logoutSuccessUrl("/login?logout")
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		.and()
		  .csrf();
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}