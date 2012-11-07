package security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Player;
import model.PlayerImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import constants.Keys;

public class AuthenticationHandler implements AuthenticationSuccessHandler  {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		PlayerImpl player = (PlayerImpl) auth.getPrincipal();
		request.getSession().setAttribute(Keys.SESSION_USER, (Player)player);
		response.sendRedirect("welcome-screen.jsp");
	}

}
