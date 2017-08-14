package library.configuration;

import library.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new RoleBasedAuthenticationSuccessHandler("/success");
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/bower_components/**", "/css/**", "/dist/**", "/plugins/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.sessionManagement().enableSessionUrlRewriting(false);

		http.authorizeRequests()
				.antMatchers("/", "/public/**").permitAll()
				.antMatchers("/manage/**").hasAnyRole("STAFF", "MANAGER")
				.antMatchers("/manage/user/**").hasRole("ADMIN")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.successHandler(successHandler())
				.loginPage("/login")
				.failureUrl("/login?error")
				.usernameParameter("idNumber")
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("remember-me")
				.logoutSuccessUrl("/")
				.permitAll()
				.and()
				.rememberMe().
				and().csrf().disable();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public UserDetailsService userDetailsService()
	{
		return super.userDetailsService();
	}

}