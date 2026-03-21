package com.example.fundtransfer.service;

import com.example.fundtransfer.entity.Customer;
import com.example.fundtransfer.repository.CustomerRepository;
import com.example.fundtransfer.exception.CustomerNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer("Kiran", "123");

        when(repository.save(customer)).thenReturn(customer);

        Customer saved = service.saveCustomer(customer);

        assertNotNull(saved);
        assertEquals("Kiran", saved.getName());
        verify(repository, times(1)).save(customer);
    }
    @Test
    void testGetAllCustomers() {
        List<Customer> list = Arrays.asList(
                new Customer("A", "1"),
                new Customer("B", "2")
        );

        when(repository.findAll()).thenReturn(list);

        List<Customer> result = service.getAllCustomers();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            service.getCustomerById(1L);
        });
    }
    @Test
    void testUpdateCustomer() {
        Customer existing = new Customer("Old", "111");
        Customer updated = new Customer("New", "222");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Customer.class))).thenReturn(updated);

        Customer result = service.updateCustomer(1L, updated);

        assertEquals("New", result.getName());
        verify(repository).save(existing);
    }
}
