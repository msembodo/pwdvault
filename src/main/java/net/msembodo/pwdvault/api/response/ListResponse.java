package net.msembodo.pwdvault.api.response;

import java.util.List;

import com.google.gson.Gson;

import net.msembodo.pwdvault.api.model.VaultAccountType;

/*
 * use @JsonPropertyOrder({ "x", "y", "x" })
 * if needs output to be ordered
 */

public class ListResponse {
	
	private boolean isListSuccess;
	private String message;
	private List<VaultAccountType> accountTypes;
	
	public ListResponse(boolean isListSuccess, String message, List<VaultAccountType> accountTypes) {
		super();
		this.isListSuccess = isListSuccess;
		this.message = message;
		this.accountTypes = accountTypes;
	}
	
	public ListResponse() {}

	public boolean isListSuccess() {
		return isListSuccess;
	}

	public void setListSuccess(boolean isListSuccess) {
		this.isListSuccess = isListSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<VaultAccountType> getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(List<VaultAccountType> accountTypes) {
		this.accountTypes = accountTypes;
	}

	// for testing
	public String toJson() {
		return new Gson().toJson(this);
	}

}
