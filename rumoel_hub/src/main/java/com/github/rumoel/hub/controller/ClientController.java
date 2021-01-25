package com.github.rumoel.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.rumoel.hub.service.ClientService;
import com.github.rumoel.libs.core.model.Client;

@RestController
public class ClientController {
	@Autowired
	ClientService clientService;

	@GetMapping("/clients")
	public List<Client> getAllclients() {
		return clientService.getAllClients();
	}

	// creating a get mapping that retrieves the detail of a specific clients
	@GetMapping("/clients/{clientid}")
	public Client getclients(@PathVariable("clientid") int clientid) {
		return clientService.getClientById(clientid);
	}

	// creating a delete mapping that deletes a specified clients
	@DeleteMapping("/clients/{clientid}")
	public void deleteClients(@PathVariable("clientid") int clientid) {
		clientService.delete(clientid);
	}

	// creating post mapping that post the clients detail in the database
	@PostMapping("/clients")
	public int saveClients(@RequestBody Client clients) {
		clientService.saveOrUpdate(clients);
		return clients.getId();
	}

	// creating put mapping that updates the clients detail
	@PutMapping("/clients")
	public Client update(@RequestBody Client clients) {
		clientService.saveOrUpdate(clients);
		return clients;
	}
}