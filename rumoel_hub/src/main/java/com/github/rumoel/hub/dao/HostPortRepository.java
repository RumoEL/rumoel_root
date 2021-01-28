package com.github.rumoel.hub.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.rumoel.libs.recon.info.HostPort;

@Repository
public interface HostPortRepository extends CrudRepository<HostPort, String> {
}
