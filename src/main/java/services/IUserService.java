package services;

import Model.UserModel;

public interface IUserService {
	UserModel login(String usernam, String password);
	
	UserModel FindByUsername(String username);

}
