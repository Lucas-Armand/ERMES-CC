package com.ermes.api.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.ermes.api.models.Business;

@Transactional
public interface BusinessRepository extends CrudRepository<Business, Long>
{
	
}
