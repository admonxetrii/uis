package np.com.nishamwagle.uis.dao;

import java.util.List;

import np.com.nishamwagle.uis.model.User;

public interface UserDao {

	int saveUser(User user);
	int editUser(User user);
	
	List<User> getAllUserInfo();
	
	void deleteUserInfo(int id);
}
