package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class DeleteTokenResponse {
	
	private boolean isDeleteTokenSuccess;
	private String message;

	public DeleteTokenResponse(boolean isDeleteTokenSuccess, String message) {
		super();
		this.isDeleteTokenSuccess = isDeleteTokenSuccess;
		this.message = message;
	}

	public DeleteTokenResponse() {
		super();
	}

	public boolean isDeleteTokenSuccess() {
		return isDeleteTokenSuccess;
	}

	public void setDeleteTokenSuccess(boolean isDeleteTokenSuccess) {
		this.isDeleteTokenSuccess = isDeleteTokenSuccess;
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
