package com.github.rumoel.recon.portscan.spring;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.rumoel.libs.recon.info.HostPort;
import com.github.rumoel.recon.portscan.header.PortScanHeader;

public class PortscanSpringClientService {
	private PortscanSpringClientService() {
	}

	public static void sendResult(HostPort data) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HostPort> requestBody = new HttpEntity<>(data, headers);

		ResponseEntity<HostPort> result = PortScanHeader.restTemplate.postForEntity(
				//
				PortScanHeader.config.getApiAddr(),
				//
				requestBody,
				//
				HostPort.class);

		// Code = 200.
		if (result.getStatusCode() == HttpStatus.OK) {
			HostPort resultBody = result.getBody();
			System.out.println("(Client Side) Employee Created: {}" + resultBody);
		}
	}
}
