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
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public Response getAllClients() {
		List<Client> clients = clientRepository.findAll();
		return Response.ok(clients).build();
	}

	@GET
	@Path("{id}")
	public Response getClientById(@PathParam(value = "id") Long id) throws ResourceNotFoundException {
		Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found :: " + id));
		return Response.ok(client).build();
	}

	@POST
	@Path("/findbyemail")
	public Response getClientByEmail(@PathParam(value = "email") String email) throws ResourceNotFoundException {
		Client client = clientRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Client not found with email "+email));
		return Response.ok(client).build();
	}

	@POST
	@Path("/new")
	public Response createClient(Client c) {
		final Client client = clientRepository.save(c);
		return Response.ok(client).build();
	}

	@PUT
	public Response updateClient(@Valid Client c) throws ResourceNotFoundException {
		Client client = clientRepository.findById(c.getId()).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		client = c;
		return Response.ok(client).build();
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
