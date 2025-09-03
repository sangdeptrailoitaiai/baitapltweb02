package dao;

import java.util.List;
import Model.UserModel;

public interface IUserDao {
	List<UserModel> findAll();
	
	UserModel findById(int id);
	
    int insert(UserModel user); 
    
	UserModel findByUsername (String username);
	
    UserModel findByEmail(String email);
    int upsertResetToken(int userId, String token, int expireMinutes);
    boolean isResetTokenExpired(String token);
    int updatePasswordByToken(String token, String newHashedPassword);


}
