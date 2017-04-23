package net.msembodo.pwdvault.api.dao;

import net.msembodo.pwdvault.api.model.User;

public interface NativeQueryDao {
	
	public User findUserBySessionKey(String sessionKey);

}
