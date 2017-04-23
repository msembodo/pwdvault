package net.msembodo.pwdvault.api.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.msembodo.pwdvault.api.dao.NativeQueryDao;
import net.msembodo.pwdvault.api.model.User;
import net.msembodo.pwdvault.api.model.UserMapper;

@Repository
public class NativeQueryDaoImpl implements NativeQueryDao {
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public User findUserBySessionKey(String sessionKey) {
		String sql = "select t1.id, t1.email, t1.name, t1.password_hash from users t1 "
				+ "inner join sessions t2 on t1.id = t2.s_user_id where t2.session_key = ?";
		User user = (User) jdbc.queryForObject(sql, new Object[] {sessionKey}, new UserMapper());
		return user;
	}

}
