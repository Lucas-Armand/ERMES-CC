package com.ermes.api.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.ermes.api.models.User;
import com.ermes.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController
{
	@Autowired
	private UserService userService;

	// GET /users
	// Retrieves a list of users

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getUsers(@RequestParam(value = "email", required = false) String email)
	{
		if (email != null)
		{
			return getUserByEmail(email);
		}

		List<User> users = userService.getUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// POST /users
	// Creates a new user

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<User> createUser(@RequestBody User user)
	{
		userService.saveUser(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}

	ResponseEntity<User> getUserByEmail(String email)
	{
		User user = userService.getUserByEmail(email);

		if (user == null)
		{
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// GET /users/{userId}
	// Retrieves a specific user

	@RequestMapping(value = "/{userId}")
	ResponseEntity<User> getUserById(@PathVariable Long userId)
	{
		User user = userService.getUserById(userId);

		if (user == null)
		{
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// PUT /users/{userId}
	// Updates a specific user

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	ResponseEntity<User> updateUserById(@PathVariable Long userId, @RequestBody User user)
	{
		userService.updateUserById(userId, user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	// DELETE /users/{userId}
	// Deletes a specific user

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	ResponseEntity<User> deleteUserById(@PathVariable Long userId)
	{
		userService.deleteUser(userId);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
}