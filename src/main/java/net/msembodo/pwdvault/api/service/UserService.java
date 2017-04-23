package net.msembodo.pwdvault.api.service;

import net.msembodo.pwdvault.api.response.LoginResponse;
import net.msembodo.pwdvault.api.response.LogoutResponse;

public interface UserService {
	
	public LoginResponse login(String email, String password);
	public LogoutResponse logout(String sessionKey);
	public boolean isAuthenticated(String sessionKey);

}
