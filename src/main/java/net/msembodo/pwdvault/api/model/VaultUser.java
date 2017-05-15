package net.msembodo.pwdvault.api.model;

public class VaultUser {
	
	private String email;
	private String name;
	
	public VaultUser(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}

	public VaultUser() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
