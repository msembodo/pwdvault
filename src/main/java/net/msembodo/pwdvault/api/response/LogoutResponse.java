package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class LogoutResponse {
	
	private boolean logoutSuccess;
	private String message;
	
	public LogoutResponse() {}

	public LogoutResponse(boolean logoutSuccess) {
		super();
		this.logoutSuccess = logoutSuccess;
	}

	public boolean isLogoutSuccess() {
		return logoutSuccess;
	}

	public void setLogoutSuccess(boolean logoutSuccess) {
		this.logoutSuccess = logoutSuccess;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// for testing
	public String toJson() {
		return new Gson().toJson(this);
	}

}
