package com.github.rumoel.hub.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.rumoel.libs.core.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

}
