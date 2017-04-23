package net.msembodo.pwdvault.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.msembodo.pwdvault.api.model.VaultData;
import net.msembodo.pwdvault.api.response.DeleteTokenResponse;
import net.msembodo.pwdvault.api.response.ListResponse;
import net.msembodo.pwdvault.api.response.LoginResponse;
import net.msembodo.pwdvault.api.response.LogoutResponse;
import net.msembodo.pwdvault.api.response.RetrieveTokenResponse;
import net.msembodo.pwdvault.api.response.TokenizeResponse;
import net.msembodo.pwdvault.api.response.UpdateTokenResponse;
import net.msembodo.pwdvault.api.service.UserService;
import net.msembodo.pwdvault.api.service.VaultService;

@RestController
public class VaultController {
	
	@Autowired
	private UserService userService;
	
	// for testing
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private VaultService vaultService;
	
	// for testing
	public void setVaultService(VaultService vaultService) {
		this.vaultService = vaultService;
	}

	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public LoginResponse login(@ModelAttribute("email") String email, @ModelAttribute("password") String password) {
		return userService.login(email, password);
	}
	
	@RequestMapping("/api/logout")
	public LogoutResponse logout(String sessionKey) {
		return userService.logout(sessionKey);
	}
	
	@RequestMapping(value = "/api/tokenize", method = RequestMethod.POST)
	public TokenizeResponse tokenize(@RequestParam("sessionKey") String sessionKey, 
			@ModelAttribute("accountName") String accountName, @ModelAttribute("accountType") String accountType, 
			@ModelAttribute("description") String description, @ModelAttribute("password") String password, 
			@ModelAttribute("pinCode") String pinCode) {
		
		if (userService.isAuthenticated(sessionKey)) {
			VaultData vaultData = new VaultData(accountName, accountType, description, password);
			return vaultService.tokenize(sessionKey, pinCode, vaultData);
			
		} else 
			return new TokenizeResponse(false, null, "user is not authenticated");
	}
	
	@RequestMapping(value = "/api/update", method = RequestMethod.POST)
	public UpdateTokenResponse update(@RequestParam("sessionKey") String sessionKey, @ModelAttribute("tokenId") String tokenId, 
			@ModelAttribute("accountName") String accountName, @ModelAttribute("accountType") String accountType, 
			@ModelAttribute("description") String description, @ModelAttribute("password") String password,
			@ModelAttribute("pinCode") String pinCode) {
		
		if (userService.isAuthenticated(sessionKey)) {
			VaultData vaultData = new VaultData(accountName, accountType, description, password);
			return vaultService.update(sessionKey, Long.parseLong(tokenId), pinCode, vaultData);
			
		} else
			return new UpdateTokenResponse(false, "user is not authenticated");
	}
	
	@RequestMapping("/api/retrieve")
	public RetrieveTokenResponse retrieve(String sessionKey, long tokenId, String pinCode) {
		if (userService.isAuthenticated(sessionKey)) 
			return vaultService.retrieve(sessionKey, tokenId, pinCode);
		else
			return new RetrieveTokenResponse(false, "user is not authenticated", null);
	}
	
	@RequestMapping("/api/remove")
	public DeleteTokenResponse remove(String sessionKey, long tokenId) {
		if (userService.isAuthenticated(sessionKey))
			return vaultService.remove(sessionKey, tokenId);
		else
			return new DeleteTokenResponse(false, "user is not authenticated");
	}
	
	@RequestMapping("/api/list")
	public ListResponse list(String sessionKey) {
		if (userService.isAuthenticated(sessionKey))
			return vaultService.list(sessionKey);
		else
			return new ListResponse(false, "user is not authenticated", null);		
	}

}
