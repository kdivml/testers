package hu.idomsoft.testers.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.requestCache().requestCache(new CustomRequestCache())
			.and().authorizeRequests()
			.requestMatchers(SecurityUtils::isFrameWorkInternalRequest).permitAll()
			
			.anyRequest().authenticated()
			
			.and().formLogin()
			.loginPage(LOGIN_URL).permitAll()
			.loginProcessingUrl(LOGIN_PROCESSING_URL)
			.failureUrl(LOGIN_FAILURE_URL)
			.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
			"/VAADIN/**",
			"/favicon.ico",
			"/robots.txt",
			"/manifest.webmanifest",
			"/sw.js",
			"/offline.html",
			"/icons/**",
			"/images/**",
			"/styles/**",
			"/h2-console/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		try(InputStream file = new FileInputStream("/var/testers/testers.properties")) {
			Properties properties = new Properties();
		    properties.load(file);
		    
			auth.ldapAuthentication()
				.userDnPatterns("uid={0}, ou=People")
				.groupSearchBase("ou=People")
				.contextSource()
					.url("ldap://" + properties.getProperty("ldap_server_address") + "/dc=iffo,dc=local");
		}
		catch (IOException ex) {
	    	Logger.getLogger(SecurityConfiguration.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
