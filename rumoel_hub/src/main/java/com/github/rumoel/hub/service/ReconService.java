package com.github.rumoel.hub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rumoel.hub.dao.HostPortRepository;
import com.github.rumoel.libs.recon.info.HostPort;

@Service
public class ReconService {
	@Autowired
	HostPortRepository hostPortRepository;
	Logger logger = LoggerFactory.getLogger(getClass());

	public void save(HostPort data) {
		boolean contain = hostPortRepository.existsById(data.getId());
		if (!contain) {
			hostPortRepository.save(data);
			logger.info("{} is saved", data);
		}
	}
}
