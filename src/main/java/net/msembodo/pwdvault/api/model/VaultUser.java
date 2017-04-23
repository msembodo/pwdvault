package net.msembodo.pwdvault.api.model;

public class VaultUser {
	
	private String email;
	private String Name;
	
	public VaultUser(String email, String name) {
		super();
		this.email = email;
		Name = name;
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
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
