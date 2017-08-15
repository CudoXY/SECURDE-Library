package library.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
{

	@Autowired
	private MessageSource messages;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
	{
		super.onAuthenticationFailure(request, response, exception);
		String errorMessage = messages.getMessage("message.badCredentials", null, null);
		if (exception.getMessage().equalsIgnoreCase("blocked")) {
			errorMessage = messages.getMessage("auth.message.blocked", null, null);
		}
	}
}