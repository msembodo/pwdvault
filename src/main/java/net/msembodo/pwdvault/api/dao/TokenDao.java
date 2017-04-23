package net.msembodo.pwdvault.api.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import net.msembodo.pwdvault.api.model.Token;

@Transactional
public interface TokenDao extends CrudRepository<Token, Long> {

}
