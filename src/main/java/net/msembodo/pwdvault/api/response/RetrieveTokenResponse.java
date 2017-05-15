package net.msembodo.pwdvault.api.response;

import com.google.gson.Gson;

import net.msembodo.pwdvault.api.model.VaultData;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class RetrieveTokenResponse {
	
	private boolean retrieveTokenSuccess;
	private String message;
	private VaultData vaultData;
	
	public RetrieveTokenResponse(boolean retrieveTokenSuccess, String message, VaultData vaultData) {
		super();
		this.retrieveTokenSuccess = retrieveTokenSuccess;
		this.message = message;
		this.vaultData = vaultData;
	}

	public RetrieveTokenResponse() {
		super();
	}

	public boolean isRetrieveTokenSuccess() {
		return retrieveTokenSuccess;
	}

	public void setRetrieveTokenSuccess(boolean retrieveTokenSuccess) {
		this.retrieveTokenSuccess = retrieveTokenSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VaultData getVaultData() {
		return vaultData;
	}

	public void setVaultData(VaultData vaultData) {
		this.vaultData = vaultData;
	}
	
	// for testing
	public String toJson() {
		return new Gson().toJson(this);
	}

}
