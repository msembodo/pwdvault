package net.msembodo.pwdvault.api.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import net.msembodo.pwdvault.api.model.User;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {
	
	public User findByEmail(String email);

}
