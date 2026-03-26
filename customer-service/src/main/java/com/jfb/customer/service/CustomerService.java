package com.jfb.customer.service;

import com.jfb.customer.dto.CustomerCreateRequest;
import com.jfb.customer.dto.CustomerResponse;
import com.jfb.customer.dto.CustomerUpdateRequest;
import com.jfb.customer.exception.CustomerAlreadyExistsException;
import com.jfb.customer.exception.CustomerNotFoundException;
import com.jfb.customer.mapper.CustomerMapper;
import com.jfb.customer.model.Customer;
import com.jfb.customer.model.CustomerStatus;
import com.jfb.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Transactional
    public CustomerResponse create(CustomerCreateRequest request) {
        validateUniqueCustomer(request.cpf(), request.email());

        Customer customer = mapper.toEntity(request);
        customer.setStatus(CustomerStatus.PENDING);
        customer.setCustomerCode(generateCustomerCode());

        Customer saved = repository.save(customer);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        return mapper.toResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findByCpf(String cpf) {
        Customer customer = repository.findByCpf(cpf)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with cpf: " + cpf));

        return mapper.toResponse(customer);
    }

    @Transactional
    public CustomerResponse update(UUID id, CustomerUpdateRequest request) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        mapper.updateCustomerFromRequest(request, customer);

        Customer updated = repository.save(customer);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void deactivate(UUID id, String reason) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        customer.setStatus(CustomerStatus.INACTIVE);
        customer.setDeactivatedAt(Instant.now());
        customer.setDeactivatedReason(reason);

        repository.save(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validateUniqueCustomer(String cpf, String email) {
        if (repository.existsByCpf(cpf)) {
            throw new CustomerAlreadyExistsException("CPF already registered: " + cpf);
        }
        if (repository.existsByEmail(email)) {
            throw new CustomerAlreadyExistsException("Email already registered: " + email);
        }
    }

    private String generateCustomerCode() {
        long timestamp = System.currentTimeMillis();
        return "CUST-" + String.format("%08d", timestamp % 100_000_000);
    }
}