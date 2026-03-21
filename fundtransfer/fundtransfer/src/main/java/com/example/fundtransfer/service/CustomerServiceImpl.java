package com.example.fundtransfer.service.impl;

import com.example.fundtransfer.dto.CustomerDTO;
import com.example.fundtransfer.entity.Customer;
import com.example.fundtransfer.repository.CustomerRepository;
import com.example.fundtransfer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer customer = mapToEntity(dto);
        return mapToDTO(repository.save(customer));
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(dto.getName());
        customer.setAddressLine1(dto.getAddressLine1());
        customer.setAddressLine2(dto.getAddressLine2());
        customer.setCity(dto.getCity());
        customer.setCountry(dto.getCountry());
        customer.setPincode(dto.getPincode());

        return mapToDTO(repository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }

    // 🔁 Mapper Methods
    private Customer mapToEntity(CustomerDTO dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setAddressLine1(dto.getAddressLine1());
        c.setAddressLine2(dto.getAddressLine2());
        c.setCity(dto.getCity());
        c.setCountry(dto.getCountry());
        c.setPincode(dto.getPincode());
        return c;
    }

    private CustomerDTO mapToDTO(Customer entity) {
        return new CustomerDTO(
                entity.getId(),
                entity.getName(),
                entity.getAddressLine1(),
                entity.getAddressLine2(),
                entity.getCity(),
                entity.getCountry(),
                entity.getPincode()
        );
    }
}