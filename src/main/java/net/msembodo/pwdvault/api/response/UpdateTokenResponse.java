package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class UpdateTokenResponse {
	
	private boolean isUpdateTokenSuccess;
	private String message;

	public UpdateTokenResponse(boolean isUpdateTokenSuccess, String message) {
		super();
		this.isUpdateTokenSuccess = isUpdateTokenSuccess;
		this.message = message;
	}

	public UpdateTokenResponse() {
		super();
	}

	public boolean isUpdateTokenSuccess() {
		return isUpdateTokenSuccess;
	}

	public void setUpdateTokenSuccess(boolean isUpdateTokenSuccess) {
		this.isUpdateTokenSuccess = isUpdateTokenSuccess;
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
