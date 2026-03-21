package com.example.fundtransfer.service;

import com.example.fundtransfer.dto.CustomerDTO;
import com.example.fundtransfer.entity.Customer;
import com.example.fundtransfer.repository.CustomerRepository;
import com.example.fundtransfer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private final CustomerRepository repository = mock(CustomerRepository.class);
    private final CustomerService service = new CustomerServiceImpl(repository);

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Kiran");

        when(repository.save(any())).thenReturn(customer);

        CustomerDTO dto = new CustomerDTO();
        dto.setName("Kiran");

        CustomerDTO result = service.createCustomer(dto);

        assertNotNull(result);
        assertEquals("Kiran", result.getName());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test");

        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDTO result = service.getCustomerById(1L);

        assertEquals("Test", result.getName());
    }
}