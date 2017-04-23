package net.msembodo.pwdvault.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "sessions")
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "session_key")
	@NotNull
	private String sessionKey;
	
	@Column(name = "is_active")
	@Type(type = "true_false")
	@NotNull
	private boolean isActive;
	
	@Column(name = "login_date_time")
	@NotNull
	private LocalDateTime loginDateTime;
	
	@ManyToOne
	@JoinColumn
	private User sUser;
	
	public Session() {}

	public Session(String sessionKey, boolean isActive, LocalDateTime loginDateTime, User sUser) {
		super();
		this.sessionKey = sessionKey;
		this.isActive = isActive;
		this.loginDateTime = loginDateTime;
		this.sUser = sUser;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(LocalDateTime loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public User getsUser() {
		return sUser;
	}

	public void setsUser(User sUser) {
		this.sUser = sUser;
	}

}
