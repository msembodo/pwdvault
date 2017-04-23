package net.msembodo.pwdvault.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.msembodo.pwdvault.api.dao.SessionDao;
import net.msembodo.pwdvault.api.dao.TokenDao;
import net.msembodo.pwdvault.api.model.Session;
import net.msembodo.pwdvault.api.model.Token;
import net.msembodo.pwdvault.api.model.User;
import net.msembodo.pwdvault.api.model.VaultAccountType;
import net.msembodo.pwdvault.api.model.VaultData;
import net.msembodo.pwdvault.api.response.DeleteTokenResponse;
import net.msembodo.pwdvault.api.response.ListResponse;
import net.msembodo.pwdvault.api.response.RetrieveTokenResponse;
import net.msembodo.pwdvault.api.response.TokenizeResponse;
import net.msembodo.pwdvault.api.response.UpdateTokenResponse;
import net.msembodo.pwdvault.api.service.CryptoService;
import net.msembodo.pwdvault.api.service.VaultService;

@Service
public class VaultServiceImpl implements VaultService {
	
	@Autowired
	private CryptoService cryptoService;
	
	@Autowired
	private SessionDao sessionDao;
		
	@Autowired
	private TokenDao tokenDao;

	@Override
	public TokenizeResponse tokenize(String sessionKey, String pinCode, VaultData vaultData) {
		try {
			// get session user
			Session session = sessionDao.findBySessionKey(sessionKey);
			User user = session.getsUser();
			
			String tokenString = cryptoService.encryptToken(vaultData.toJson(), pinCode); // encrypt data
			Token token = new Token(vaultData.getAccountType(), tokenString, user); // create token
			tokenDao.save(token); // record token
			
			return new TokenizeResponse(true, token.getId(), "tokenization success"); // response
			
		} catch (Exception ex) {
			return new TokenizeResponse(false, null, ex.toString());
		} 	
	}

	@Override
	public UpdateTokenResponse update(String sessionKey, long tokenId, String pinCode, VaultData vaultData) {
		try {
			// get session user
			Session session = sessionDao.findBySessionKey(sessionKey);
			User user = session.getsUser();
			
			// retrieve token to update
			Token token = tokenDao.findOne(tokenId); 
			if (token == null)
				throw new Exception("token does not exist");
			
			// check if the active user is the one who updates token
			if (token.gettUser().getId() == user.getId()) {
				String tokenString = cryptoService.encryptToken(vaultData.toJson(), pinCode); // encrypt data
				token.setToken(tokenString); // update token with new data
				tokenDao.save(token); // record updated token
				
				return new UpdateTokenResponse(true, "update success"); // response
				
			} else
				throw new Exception("update violation");
			
		} catch (Exception ex) {
			return new UpdateTokenResponse(false, ex.toString());
		}
	}

	@Override
	public RetrieveTokenResponse retrieve(String sessionKey, long tokenId, String pinCode) {
		try {
			Session session = sessionDao.findBySessionKey(sessionKey); // get session object
			
			// retrieve token
			Token token = tokenDao.findOne(tokenId); 
			if (token == null)
				throw new Exception("token does not exist");
			
			// check if the active user is the one who retrieves token
			if (token.gettUser().getId() == session.getsUser().getId()) {
				String wrappedToken = cryptoService.decryptToken(token.getToken(), pinCode); // decrypt data
				
				// convert json to object
				Gson gson = new Gson(); 
				VaultData vaultData = gson.fromJson(wrappedToken, VaultData.class);
				
				return new RetrieveTokenResponse(true, "retrieve success", vaultData); // response
				
			} else
				throw new Exception("retrieve violation");
			
		} catch (Exception ex) {
			return new RetrieveTokenResponse(false, ex.toString(), null);
		}
	}

	@Override
	public DeleteTokenResponse remove(String sessionKey, long tokenId) {
		try {
			// get session user
			Session session = sessionDao.findBySessionKey(sessionKey);
			User user = session.getsUser();
			
			// retrieve token
			Token token = tokenDao.findOne(tokenId); 
			if (token == null)
				throw new Exception("token does not exist");
			
			// check if the active user is the one who deletes token
			if (user.getId() == token.gettUser().getId()) {
				tokenDao.delete(token); // delete token
				return new DeleteTokenResponse(true, "delete success"); // response
				
			} else
				throw new Exception("delete violation");
			
			
		} catch (Exception ex) {
			return new DeleteTokenResponse(false, ex.toString());
		}
	}

	@Override
	public ListResponse list(String sessionKey) {
		try {
			// get user by session key
			Session session = sessionDao.findBySessionKey(sessionKey);
			User user = session.getsUser();
			
			Set<Token> tokens = user.getTokens(); // get all tokens for this user
			
			// create list of account type
			List<VaultAccountType> accountTypes = new ArrayList<>();
			for (Token token : tokens) 
				accountTypes.add(new VaultAccountType(token.getId(), token.getAccountType()));
			
			return new ListResponse(true, "query success", accountTypes); // response
			
		} catch (Exception ex) {
			return new ListResponse(false, ex.toString(), null);
		}
	}

}
