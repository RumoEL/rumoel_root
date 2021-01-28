package com.github.rumoel.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.rumoel.hub.service.ReconService;
import com.github.rumoel.libs.recon.info.HostPort;

@RestController
public class ReconController {
	@Autowired
	ReconService reconService;

	@GetMapping(path = "/api/recon")
	public void getIndex() {
		System.err.println("recon2:index");
	}

	@PostMapping(path = "/api/insecure/recon", consumes = "application/json", produces = "application/json")
	public void add(@RequestBody HostPort hostPort) {
		reconService.save(hostPort);
	}
}
