package library.domain.helper;

import library.domain.User;
import library.services.user.UserService;
import org.apache.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by CudoXY on 8/14/2017.
 */
public class UserHelper
{
	public static User getCurrentUser(UserService userService)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken)
			return null;

		System.out.println("UserService :: auth.getName() = " + auth.getName());

		return userService.getUserById(Integer.parseInt(auth.getName()));
	}

	public static void logoutUser(HttpServletRequest request)
	{
		new SecurityContextLogoutHandler().logout(request, null, null);
	}
}
