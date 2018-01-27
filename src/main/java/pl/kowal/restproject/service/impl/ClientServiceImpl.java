package pl.kowal.restproject.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;
import pl.kowal.restproject.domain.Client;
import pl.kowal.restproject.repository.ClientRepository;
import pl.kowal.restproject.service.ClientService;

@Service
@Validated
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Inject
    public ClientServiceImpl(final ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Client save(@NotNull @Valid final Client client) {
        log.debug("Creating {}", client);
        return repository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getList() {
        log.debug("Retrieving the list of all Clients");
        return repository.findAll();
    }

}
