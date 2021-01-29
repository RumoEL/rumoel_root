package com.github.rumoel.hub.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		//
				csrf().ignoringAntMatchers("/api/insecure/**").and().

				//
				authorizeRequests().antMatchers(HttpMethod.POST, "/api/**", "/api/recon").permitAll().and().
				//
				authorizeRequests().antMatchers("/", "/about", "/register").permitAll().and()
				//
				//
				.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").and()
				//
				.authorizeRequests().anyRequest().fullyAuthenticated().and()
				//
				.formLogin().loginPage("/login").permitAll().and()
				//
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username, password, active from usr where username=?")
				.authoritiesByUsernameQuery(
						"select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");
	}
}