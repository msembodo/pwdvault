package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class UpdateTokenResponse {
	
	private boolean updateTokenSuccess;
	private String message;

	public UpdateTokenResponse(boolean updateTokenSuccess, String message) {
		super();
		this.updateTokenSuccess = updateTokenSuccess;
		this.message = message;
	}

	public UpdateTokenResponse() {
		super();
	}

	public boolean isUpdateTokenSuccess() {
		return updateTokenSuccess;
	}

	public void setUpdateTokenSuccess(boolean updateTokenSuccess) {
		this.updateTokenSuccess = updateTokenSuccess;
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
