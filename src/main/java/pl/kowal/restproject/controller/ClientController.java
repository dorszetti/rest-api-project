package pl.kowal.restproject.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.kowal.restproject.domain.Client;
import pl.kowal.restproject.service.ClientService;

@RestController
@Slf4j
public class ClientController extends AbstractController {

	private final ClientService clientService;

	@Inject
	public ClientController(final ClientService clientService) {
		this.clientService = clientService;
	}

	@RequestMapping(value = "/client", method = RequestMethod.POST)
	public Client createClient(@RequestBody @Valid final Client client) {
		log.debug("Creating {}", client);
		return clientService.save(client);
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public List<Client> listClients() {
		log.debug("Listing all clients");
		return clientService.getList();
	}

}
