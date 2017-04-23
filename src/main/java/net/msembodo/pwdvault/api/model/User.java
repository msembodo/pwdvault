package net.msembodo.pwdvault.api.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	private String email;
	
	@NotNull
	private String name;
	
	@Column(name = "password_hash")
	@NotNull
	private String passwordHash;
	
	@OneToMany(mappedBy = "tUser", cascade = CascadeType.ALL)
	private Set<Token> tokens;
	
	@OneToMany(mappedBy = "sUser", cascade = CascadeType.ALL)
	private Set<Session> sessions;

	public User() {
		super();
	}

	public User(String email, String name, String passwordHash) {
		super();
		this.email = email;
		this.name = name;
		this.passwordHash = passwordHash;
	}

	public User(long id, String email, String name, String passwordHash) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.passwordHash = passwordHash;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Set<Token> getTokens() {
		return tokens;
	}

	public void setTokens(Set<Token> tokens) {
		this.tokens = tokens;
	}

	public Set<Session> getSessions() {
		return sessions;
	}

	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}

}
