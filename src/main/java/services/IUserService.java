package services;

import Model.UserModel;

public interface IUserService {
	UserModel login(String usernam, String password);
	
	UserModel FindByUsername(String username);
	
    UserModel findByEmail(String email);
    boolean register(UserModel user);
    boolean setResetToken(int userId, String token, int expireMinutes);
    boolean isResetTokenExpired(String token);
    boolean resetPassword(String token, String newPassword);

}
