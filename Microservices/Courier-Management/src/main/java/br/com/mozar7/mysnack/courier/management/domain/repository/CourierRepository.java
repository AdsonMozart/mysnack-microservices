package br.com.mozar7.mysnack.courier.management.domain.repository;

import br.com.mozar7.mysnack.courier.management.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
}
