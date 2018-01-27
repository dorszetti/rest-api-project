package pl.kowal.restproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kowal.restproject.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
