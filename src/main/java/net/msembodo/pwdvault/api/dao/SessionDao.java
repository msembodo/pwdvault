package net.msembodo.pwdvault.api.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import net.msembodo.pwdvault.api.model.Session;

@Transactional
public interface SessionDao extends CrudRepository<Session, Long> {
	
	public Session findBySessionKey(String sessionKey);

}
