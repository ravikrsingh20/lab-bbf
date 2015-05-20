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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
		.antMatchers("/validate/**").access("hasIpAddress('137.226.112.106')")
		//.access("hasIpAddress('137.226.112.105') or hasIpAddress('137.226.112.104') or hasIpAddress('137.226.112.106') or hasIpAddress('137.226.112.108') or hasIpAddress('137.226.112.109') or  hasIpAddress('137.226.112.110') or hasIpAddress('137.226.112.92')  or hasIpAddress('137.226.112.92')")
		.antMatchers("/emp/**").hasAnyAuthority("EMPL","ADMN")
		.antMatchers("/onln/**").hasAuthority("CUST")
		.antMatchers("/","/atmbank/**","/login/**","/logout/**","/register/**","/resources/**","/validate/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").successHandler(successHandler).failureUrl("/login?error").permitAll()
		.usernameParameter("username").passwordParameter("password")
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher( "/logout" )).logoutSuccessUrl("/login?logout").permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID")	
		.and()
		.exceptionHandling().accessDeniedPage("/403")			
//		.and()
//		.requiresChannel().antMatchers("/**").requiresSecure()
		.and()		
		.csrf().disable()
//		.and()
//		.sessionManagement().maximumSessions( 1 )
		;
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}