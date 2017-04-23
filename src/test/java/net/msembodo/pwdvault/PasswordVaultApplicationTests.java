package net.msembodo.pwdvault;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import net.msembodo.pwdvault.api.VaultController;
//import net.msembodo.pwdvault.api.model.VaultUser;
//import net.msembodo.pwdvault.api.response.LoginResponse;
//import net.msembodo.pwdvault.api.service.UserService;
//import net.msembodo.pwdvault.api.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordVaultApplicationTests {

	@Test
	public void contextLoads() {}
	
	//@Test
//	public void testLoginApi() throws Exception {
//		UserService userService = new UserServiceImpl();
//		
//		VaultController vaultController = new VaultController();
//		vaultController.setUserService(userService);
//		
//		LoginResponse expectedLoginResponse = new LoginResponse(true, "74689736n8297fy29y2e8uy72h80", 
//				new VaultUser("martyono.sembodo@gmail.com", "Martyono Sembodo"));
//		LoginResponse actualLoginResponse = vaultController.login("martyono.sembodo@gmail.com", "12345678");
//		
//		JSONAssert.assertEquals(expectedLoginResponse.toJson(), actualLoginResponse.toJson(), false);
//	}
}
