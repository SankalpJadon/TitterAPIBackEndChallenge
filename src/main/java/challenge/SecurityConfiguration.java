package challenge;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired DataSource datasource;
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
		http.csrf().disable().authorizeRequests()
		.anyRequest().authenticated()
		.and().httpBasic()
		.authenticationEntryPoint(authEntryPoint);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	 auth.inMemoryAuthentication() 
        .withUser("batman").password("batman").roles("USER")
        .and()
        .withUser("superman").password("superman").roles("USER")
        .and()
        .withUser("catwoman").password("catwoman").roles("USER")
        .and()
        .withUser("daredevil").password("daredevil").roles("USER")
        .and()
        .withUser("alfred").password("alfred").roles("USER")
        .and()
        .withUser("dococ").password("dococ").roles("USER")
        .and()
        .withUser("zod").password("zod").roles("USER")
        .and()
        .withUser("spiderman").password("spiderman").roles("USER")
        .and()
        .withUser("ironman").password("ironman").roles("USER")
        .and()
        .withUser("profx").password("profx").roles("USER");    
    }
}