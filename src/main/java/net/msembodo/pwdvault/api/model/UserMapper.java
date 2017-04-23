package net.msembodo.pwdvault.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowIndex) throws SQLException {
		User user = new User(rs.getLong("id"), rs.getString("email"), rs.getString("name"), rs.getString("password_hash"));
		return user;
	}

}
