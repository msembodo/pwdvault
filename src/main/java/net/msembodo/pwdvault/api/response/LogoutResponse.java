package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class LogoutResponse {
	
	private boolean isLogoutSuccess;
	private String message;
	
	public LogoutResponse() {}

	public LogoutResponse(boolean isLogoutSuccess) {
		super();
		this.isLogoutSuccess = isLogoutSuccess;
	}

	public boolean isLogoutSuccess() {
		return isLogoutSuccess;
	}

	public void setLogoutSuccess(boolean isLogoutSuccess) {
		this.isLogoutSuccess = isLogoutSuccess;
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
