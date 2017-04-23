package net.msembodo.pwdvault.api.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.msembodo.pwdvault.api.dao.SessionDao;
import net.msembodo.pwdvault.api.dao.UserDao;
import net.msembodo.pwdvault.api.model.Session;
import net.msembodo.pwdvault.api.model.User;
import net.msembodo.pwdvault.api.model.VaultUser;
import net.msembodo.pwdvault.api.response.LoginResponse;
import net.msembodo.pwdvault.api.response.LogoutResponse;
import net.msembodo.pwdvault.api.service.CryptoService;
import net.msembodo.pwdvault.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CryptoService cryptoService;
	
	@Autowired
	private SessionDao sessionDao;

	@Override
	public LoginResponse login(String email, String password) {
		LoginResponse response = new LoginResponse();
		try {
			User user = userDao.findByEmail(email);
			if (user == null)
				throw new Exception("user not found");
			
			if (user.getPasswordHash().equals(cryptoService.hash(password).toUpperCase())) {
				response.setLoginSuccess(true);
				response.setMessage("Login success");
				response.setSessionId(cryptoService.generateRandomId());
				response.setVaultUser(new VaultUser(email, user.getName()));
				
				// insert session into table
				sessionDao.save(new Session(response.getSessionId(), true, LocalDateTime.now(), user));
				
				return response;
				
			} else {
				response.setLoginSuccess(false);
				response.setMessage("Password does not match");
				response.setSessionId("");
				response.setVaultUser(null);
				return response;
			}
			
		} catch (Exception ex) {
			response.setLoginSuccess(false);
			response.setMessage("Error processing user: " + ex.toString());
			response.setSessionId("");
			response.setVaultUser(null);
			return response;
		}
	}

	@Override
	public LogoutResponse logout(String sessionKey) {
		LogoutResponse response = new LogoutResponse();
		try {
			Session session = sessionDao.findBySessionKey(sessionKey);
			if (session == null) 
				throw new Exception("Session key not found");
			
			session.setActive(false);
			sessionDao.save(session); // update session status
			
			response.setLogoutSuccess(true);
			response.setMessage("Logout success");
			return response;
			
		} catch (Exception ex) {
			response.setLogoutSuccess(false);
			response.setMessage(ex.toString());
			ex.printStackTrace();
			return response;
		}
	}

	@Override
	public boolean isAuthenticated(String sessionKey) { 
		Session session = sessionDao.findBySessionKey(sessionKey);
		if ((session != null) && (session.isActive() != false))
			return true;
		else
			return false;
	}

}
