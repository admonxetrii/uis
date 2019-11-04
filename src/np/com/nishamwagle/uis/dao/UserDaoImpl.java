package np.com.nishamwagle.uis.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import np.com.nishamwagle.uis.model.User;
import np.com.nishamwagle.uis.utl.DbConnection;

public class UserDaoImpl implements UserDao {

	public static final String INSERT_SQL = "insert into user_info_system.users(uname,pword,email,gender,hobbies,nationality,dob)values(?,?,?,?,?,?,?)";
	public static final String SELECT_SQL = "select * from user_info_system.users";
	public static final String DELETE_SQL = "delete from user_info_system.users where id = ?";
	public static final String EDIT_SQL = "update user_info_system.users set uname = ?, pword=?,email=?, gender=?,hobbies=?,nationality=?,dob=? where id=?";

	@Override
	public int saveUser(User user) {
		int saved = 0;
		try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_SQL);) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getHobbies());
			ps.setString(6, user.getNationality());
			ps.setDate(7, new Date(user.getDob().getTime()));
			saved = ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return saved;
	}
	
	@Override
	public int editUser(User user) {
		int updated = 0;
		try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(EDIT_SQL);) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getHobbies());
			ps.setString(6, user.getNationality());
			ps.setDate(7, new Date(user.getDob().getTime()));
			ps.setInt(8, user.getId());
			updated = ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return updated; 
		
	}

	@Override
	public List<User> getAllUserInfo() {
		List<User> userList = new ArrayList<>();
		try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_SQL);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("uname"));
				user.setPassword(rs.getString("pword"));
				user.setEmail(rs.getString("email"));
				user.setGender(rs.getString("gender"));
				user.setHobbies(rs.getString("hobbies"));
				user.setNationality(rs.getString("nationality"));
				user.setDob(rs.getDate("dob"));
				userList.add(user);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public void deleteUserInfo(int id) {
		try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_SQL);) {

			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (ClassNotFoundException |SQLException e) {
			e.printStackTrace();
		}
	}

	

}
