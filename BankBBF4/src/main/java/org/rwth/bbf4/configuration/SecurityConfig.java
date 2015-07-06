package org.rwth.bbf4.configuration;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.security.web.util.matcher.RequestMatcher;

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
		// Build the request matcher for CSFR protection
		RequestMatcher csrfRequestMatcher = new RequestMatcher() {
			// Disable CSRF protection on the following url and methods :
			private AntPathRequestMatcher requestMatcher =
					new AntPathRequestMatcher("/validate/**", null);
			private Pattern allowedMethods = 
					Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

			@Override
			public boolean matches(HttpServletRequest request) {
				// method matches
				if (allowedMethods.matcher(request.getMethod()).matches()) {
					return false;
				}
				// If the request match one url the CSRF protection will be disabled
				if(requestMatcher.matches(request))
					return false;
				return true;
			} 
		}; // new RequestMatcher


		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
		.and()
		.authorizeRequests().antMatchers("/emp/**").hasAnyAuthority("EMPL","ADMN")
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
		.and()
		.sessionManagement().maximumSessions( 1 )
		;
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}