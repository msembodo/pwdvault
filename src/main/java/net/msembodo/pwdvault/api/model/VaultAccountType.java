package net.msembodo.pwdvault.api.model;

public class VaultAccountType {
	
	private long tokenId;
	private String accountType;
	
	public VaultAccountType() {}

	public VaultAccountType(long tokenId, String accountType) {
		super();
		this.tokenId = tokenId;
		this.accountType = accountType;
	}

	public long getTokenId() {
		return tokenId;
	}

	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
