package com.example.fundtransfer.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Customer {

    private Long id;
    private String name;
    private String password;


    public Customer() {}

    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
}
