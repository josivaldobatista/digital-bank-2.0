package com.jfb.customer.controller;

import com.jfb.customer.dto.CustomerCreateRequest;
import com.jfb.customer.dto.CustomerResponse;
import com.jfb.customer.dto.CustomerUpdateRequest;
import com.jfb.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerCreateRequest request) {
        CustomerResponse response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        CustomerResponse response = customerService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerResponse> findByCpf(@PathVariable String cpf) {
        CustomerResponse response = customerService.findByCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid CustomerUpdateRequest request) {
        CustomerResponse response = customerService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id,
            @RequestParam String reason) {
        customerService.deactivate(id, reason);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<CustomerResponse> responses = customerService.findAll();
        return ResponseEntity.ok(responses);
    }
}
