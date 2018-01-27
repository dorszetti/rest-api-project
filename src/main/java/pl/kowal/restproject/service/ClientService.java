package pl.kowal.restproject.service;

import java.util.List;

import pl.kowal.restproject.domain.Client;

public interface ClientService {

    Client save(Client client);

    List<Client> getList();

}
