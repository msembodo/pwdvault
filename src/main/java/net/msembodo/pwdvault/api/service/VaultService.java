package net.msembodo.pwdvault.api.service;

import net.msembodo.pwdvault.api.model.VaultData;
import net.msembodo.pwdvault.api.response.DeleteTokenResponse;
import net.msembodo.pwdvault.api.response.ListResponse;
import net.msembodo.pwdvault.api.response.RetrieveTokenResponse;
import net.msembodo.pwdvault.api.response.TokenizeResponse;
import net.msembodo.pwdvault.api.response.UpdateTokenResponse;

public interface VaultService {
	
	public TokenizeResponse tokenize(String sessionKey, String pinCode, VaultData vaultData);
	public UpdateTokenResponse update(String sessionKey, long tokenId, String pinCode, VaultData vaultData);
	public RetrieveTokenResponse retrieve(String sessionKey, long tokenId, String pinCode);
	public DeleteTokenResponse remove(String sessionKey, long tokenId);
	public ListResponse list(String sessionKey);

}
