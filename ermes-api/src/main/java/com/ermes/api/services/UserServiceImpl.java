package com.ermes.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ermes.api.models.Authority;
import com.ermes.api.models.User;
import com.ermes.api.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<User> getUsers()
	{
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getUserById(Long id)
	{
		return userRepository.findOne(id);
	}
	
	@Override
	public User getUserByEmail(String email)
	{
		return userRepository.findUserByEmail(email);
	}

	@Override
	public void saveUser(User user)
	{
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setAuthority(new Authority(user.getEmail(), "ROLE_USER"));
		userRepository.save(user);
	}
	
	@Override
	public void saveAdmin(User admin)
	{
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		admin.setAuthority(new Authority(admin.getEmail(), "ROLE_ADMIN"));
		userRepository.save(admin);
	}

	@Override
	public void updateUserById(Long id, User u)
	{
		User user = userRepository.findOne(id);
		user.setEmail(u.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		user.setAuthority(u.getAuthority());
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id)
	{
		userRepository.delete(id);
	}
}
