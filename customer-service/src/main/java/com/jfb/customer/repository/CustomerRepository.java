package com.jfb.customer.repository;

import com.jfb.customer.model.Customer;
import com.jfb.customer.model.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByCustomerCode(String customerCode);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Optional<Customer> findByCpfAndStatus(String cpf, CustomerStatus status);

    boolean existsByCpfAndStatus(String cpf, CustomerStatus status);
}
