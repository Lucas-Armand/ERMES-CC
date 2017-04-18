package com.ermes.api.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.ermes.api.models.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>
{
	User findUserByEmail(String email);
}
