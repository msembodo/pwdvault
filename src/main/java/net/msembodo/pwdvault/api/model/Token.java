package net.msembodo.pwdvault.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tokens")
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	private String accountType;
	
	@Lob
	@NotNull
	private String token;
	
	@ManyToOne
	@JoinColumn
	private User tUser;
	
	public Token() {}

	public Token(String accountType, String token, User user) {
		super();
		this.accountType = accountType;
		this.token = token;
		this.tUser = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User gettUser() {
		return tUser;
	}

	public void settUser(User tUser) {
		this.tUser = tUser;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
