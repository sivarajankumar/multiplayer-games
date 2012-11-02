package control.login;

import model.User;

public class LoginController {

	public User login(String userName, String password) throws LoginException{
		if (userName == null || "".equals(userName)){
			LoginException exception = new LoginException("You must specify a user name.");
			exception.setWrongUserName(true);
			throw exception;
		}
		if (password == null || "".equals(password)){
			LoginException exception = new LoginException("You must specify a password.");
			exception.setWrongPassword(true);
			throw exception;
		}
		
		// TODO login logic
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setFirstName("John");
		user.setLastName("Doe");
		
		return user;
	}
	
}
