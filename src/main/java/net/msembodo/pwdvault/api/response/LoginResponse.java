package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

import net.msembodo.pwdvault.api.model.VaultUser;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class LoginResponse {
	
	private boolean loginSuccess;
	private String message;
	private String sessionId;
	private VaultUser vaultUser;
	
	public LoginResponse(boolean loginSuccess, String message, String sessionId, VaultUser vaultUser) {
		super();
		this.loginSuccess = loginSuccess;
		this.message = message;
		this.sessionId = sessionId;
		this.vaultUser = vaultUser;
	}

	public LoginResponse() {
		super();
	}

	public boolean isLoginSuccess() {
		return loginSuccess;
	}

	public void setLoginSuccess(boolean loginSuccess) {
		this.loginSuccess = loginSuccess;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public VaultUser getVaultUser() {
		return vaultUser;
	}

	public void setVaultUser(VaultUser vaultUser) {
		this.vaultUser = vaultUser;
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
