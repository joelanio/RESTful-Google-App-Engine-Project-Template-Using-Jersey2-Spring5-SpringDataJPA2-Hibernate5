package com.wstemplate.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.wstemplate.errorhandling.ResourceNotFoundException;
import com.wstemplate.model.entities.Client;
import com.wstemplate.model.repositories.ClientRepository;

@Component
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ClientResource {

	@Autowired
	private ClientRepository clientRepository;

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello Jersey!\n"
				+ "If you're here it means that the Jersey Web Service is working"
				;
	}

	@GET
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	@GET
	@Path("{id}")
	public ResponseEntity<Client> getClientById(@PathParam(value = "id") Long id) throws ResourceNotFoundException {
		Client c = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found :: " + id));
		return ResponseEntity.ok().body(c);
	}

	@POST
	@Path("/findbyemail")
	public ResponseEntity<Client> getClientByEmail(@PathParam(value = "email") String email) throws ResourceNotFoundException {
		Client c = clientRepository.findByEmail(email);
		if(c==null) throw new ResourceNotFoundException("Client not found :: " + email);
		return ResponseEntity.ok().body(c);
	}

	@POST
	@Path("/new")
	public Client createClient(Client c) {
		return clientRepository.save(c);
	}

	@PUT
	@Path("{id}")
	public ResponseEntity<Client> updateClient(@PathParam(value = "id") Long id,
			@Valid @RequestBody Client clientDetails) throws ResourceNotFoundException {
		Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found :: " + id));
		client.setEmail(clientDetails.getEmail());
		client.setName(clientDetails.getName());
		final Client updatedClient = clientRepository.save(client);
		return ResponseEntity.ok(updatedClient);
	}

	@DELETE
	@Path("{id}")
	public Map<String, Boolean> deleteClient(@PathParam(value = "id") Long id) throws ResourceNotFoundException {
		Client c = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found :: " + id));
		clientRepository.delete(c);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
