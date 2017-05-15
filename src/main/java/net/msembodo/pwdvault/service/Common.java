package net.msembodo.pwdvault.service;

import net.msembodo.pwdvault.api.response.DeleteTokenResponse;
import net.msembodo.pwdvault.api.response.ListResponse;
import net.msembodo.pwdvault.api.response.LoginResponse;
import net.msembodo.pwdvault.api.response.LogoutResponse;
import net.msembodo.pwdvault.api.response.RetrieveTokenResponse;
import net.msembodo.pwdvault.api.response.TokenizeResponse;
import net.msembodo.pwdvault.api.response.UpdateTokenResponse;

public interface Common {
	
	public LoginResponse login(String user, String pass) throws Exception;
	public LogoutResponse logout(String sessionKey) throws Exception;
	public ListResponse list(String sessionKey) throws Exception;
	public RetrieveTokenResponse retrieve(String sessionKey, long tokenId, String pinCode) throws Exception;
	public UpdateTokenResponse update(String sessionKey, String tokenId, String accountName, String accountType,
			String description, String password, String pinCode) throws Exception;
	public DeleteTokenResponse delete(String sessionKey, long tokenId) throws Exception;
	public TokenizeResponse add(String sessionKey, String accountName, String accountType,
			String description, String password, String pinCode) throws Exception;

}
