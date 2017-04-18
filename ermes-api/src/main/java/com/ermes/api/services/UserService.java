package com.ermes.api.services;

import com.ermes.api.models.User;
import java.util.List;

public interface UserService
{
	List<User> getUsers();

	User getUserById(Long id);
	
	User getUserByEmail(String email);

	void saveUser(User user);
	
	void saveAdmin(User admin);
	
	void updateUserById(Long id, User u);

    void deleteUser(Long id);
}