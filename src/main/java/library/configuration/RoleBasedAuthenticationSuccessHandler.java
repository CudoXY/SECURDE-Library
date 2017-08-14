package library.configuration;

import library.domain.Role;
import library.domain.User;
import library.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by CudoXY on 8/14/2017.
 */
@Component
public class RoleBasedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private UserService userService;

	public RoleBasedAuthenticationSuccessHandler()
	{

	}

	public RoleBasedAuthenticationSuccessHandler(String defaultTargetUrl)
	{
		setDefaultTargetUrl(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException
	{
		User u = userService.getUserById(Integer.parseInt(authentication.getName()));

		if (u.isTemporary())
		{
			redirectStrategy.sendRedirect(request, response, "/completeRegistration");
			return;
		}


		switch (u.getRole())
		{
			case ROLE_STUDENT:
			case ROLE_FACULTY:

				HttpSession session = request.getSession();
				if (session == null)
				{
					super.onAuthenticationSuccess(request, response, authentication);
					return;
				}
				String redirectUrl = (String) session.getAttribute("url_prior_login");
				if (redirectUrl == null)
				{
					super.onAuthenticationSuccess(request, response, authentication);
					return;
				}

				// we do not forget to clean this attribute from session
				session.removeAttribute("url_prior_login");
				// then we redirect
				getRedirectStrategy().sendRedirect(request, response, redirectUrl);

				break;
			case ROLE_STAFF:
			case ROLE_MANAGER:
			case ROLE_ADMIN:

				redirectStrategy.sendRedirect(request, response, "/manage/dashboard");

				break;
		}


	}
}
