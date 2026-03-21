package com.example.fundtransfer.service;

import com.example.fundtransfer.entity.Customer;
import com.example.fundtransfer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {


    public Customer saveCustomer(Customer customer);

    public List<Customer> getAllCustomers() ;

    public Customer getCustomerById(Long id);

    public Customer updateCustomer(Long id, Customer updatedCustomer);


}