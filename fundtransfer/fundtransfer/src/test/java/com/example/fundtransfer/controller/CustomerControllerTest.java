package com.example.fundtransfer.controller;


import com.example.fundtransfer.entity.Customer;
import com.example.fundtransfer.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CustomerService service;

    @Test
    void testCreateCustomer() throws Exception {

        Customer customer = new Customer("Kiran", "123");

        Mockito.when(service.saveCustomer(Mockito.any(Customer.class)))
                .thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name": "Kiran",
                  "password": "123"
                }
            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kiran"));
    }
    @Test
    void testGetAllCustomers() throws Exception {

        List<Customer> list = Arrays.asList(
                new Customer("A", "1"),
                new Customer("B", "2")
        );

        Mockito.when(service.getAllCustomers()).thenReturn(list);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
    @Test
    void testGetCustomerById() throws Exception {

        Customer customer = new Customer("Kiran", "123");

        Mockito.when(service.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kiran"));
    }
    @Test
    void testUpdateCustomer() throws Exception {

        Customer updated = new Customer("Updated", "456");

        Mockito.when(service.updateCustomer(Mockito.eq(1L), Mockito.any(Customer.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name": "Updated",
                  "password": "456"
                }
            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }
}
