package net.msembodo.pwdvault.api.model;

import com.google.gson.Gson;

public class VaultData {
	
	private String accountName;
	private String accountType;
	private String description;
	private String password;
	
	public VaultData(String accountName, String accountType, String description, String password) {
		super();
		this.accountName = accountName;
		this.accountType = accountType;
		this.description = description;
		this.password = password;
	}

	public VaultData() {
		super();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	// for wrapping into token
	public String toJson() {
		return new Gson().toJson(this);
	}

}
