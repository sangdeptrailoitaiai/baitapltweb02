package services.impl;

import Model.UserModel;
import dao.IUserDao;
import dao.impl.UserDaoImpl;
import services.IUserService;

public class UserService implements IUserService {

	IUserDao userDao = new UserDaoImpl();

	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.FindByUsername(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;

	}

	@Override
	public UserModel FindByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
