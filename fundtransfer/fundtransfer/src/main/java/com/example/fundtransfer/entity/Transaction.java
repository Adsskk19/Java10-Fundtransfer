package com.example.fundtransfer.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private String status; // SUCCESS / FAILED

    private String comment;

    private LocalDateTime createdAt;

    private String idempotencyKey;

    public Transaction() {
    }

    public Transaction(Long id, String fromAccount, String toAccount,
                       BigDecimal amount, String status,
                       String comment, LocalDateTime createdAt, String idempotencyKey) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = status;
        this.comment = comment;
        this.createdAt = createdAt;
        this.idempotencyKey = idempotencyKey;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFromAccount() { return fromAccount; }

    public void setFromAccount(String fromAccount) { this.fromAccount = fromAccount; }

    public String getToAccount() { return toAccount; }

    public void setToAccount(String toAccount) { this.toAccount = toAccount; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void setIdempotencyKey(String idempotencyKey) {this.idempotencyKey = idempotencyKey;}

    public String getIdempotencyKey() {return idempotencyKey;}

}
