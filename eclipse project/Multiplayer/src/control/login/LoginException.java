package control.login;

public class LoginException extends Exception {

	private static final long serialVersionUID = -4644887445693127440L;
	
	private boolean wrongUserName;
	private boolean wrongPassword;
	
	public LoginException(String message){
		super(message);
	}
	
	public boolean isWrongUserName() {
		return wrongUserName;
	}
	public void setWrongUserName(boolean wrongUserName) {
		this.wrongUserName = wrongUserName;
	}
	public boolean isWrongPassword() {
		return wrongPassword;
	}
	public void setWrongPassword(boolean wrongPassword) {
		this.wrongPassword = wrongPassword;
	}

}
