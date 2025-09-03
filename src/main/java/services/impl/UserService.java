package services.impl;

import Model.UserModel;
import dao.IUserDao;
import dao.impl.UserDaoImpl;
import services.IUserService;

public class UserService implements IUserService {

	IUserDao userDao = new UserDaoImpl();

	@Override
	public UserModel login(String username, String password) {
		if (username == null || password == null)
			return null;

		UserModel user = this.FindByUsername(username.trim());
		if (user == null)
			return null;

		String dbPass = user.getPassword();
		if (dbPass == null)
			return null;

		// Nếu DB lưu plaintext (KHÔNG khuyến nghị):
		return password.equals(dbPass.trim()) ? user : null;
	}

	@Override
	public UserModel FindByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public UserModel findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean register(UserModel user) {
		return userDao.insert(user) > 0;
	}

	@Override
	public boolean setResetToken(int userId, String token, int expireMinutes) {
		return userDao.upsertResetToken(userId, token, expireMinutes) > 0;
	}

	@Override
	public boolean isResetTokenExpired(String token) {
		return userDao.isResetTokenExpired(token);
	}

	@Override
	public boolean resetPassword(String token, String newPassword) {
		// String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
		String hashed = newPassword;
		return userDao.updatePasswordByToken(token, hashed) > 0;
	}

}
