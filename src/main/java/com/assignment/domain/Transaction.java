package com.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "charges", precision = 21, scale = 2)
    private BigDecimal charges;

    @OneToOne
    @JoinColumn(unique = true)
    private Wallet receiver;

    @OneToOne
    @JoinColumn(unique = true)
    private Wallet sender;

    /**
     * Another side of the same relationship
     */
    @ManyToOne
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private PassBook passbook;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public Transaction charges(BigDecimal charges) {
        this.charges = charges;
        return this;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    public Wallet getReceiver() {
        return receiver;
    }

    public Transaction receiver(Wallet wallet) {
        this.receiver = wallet;
        return this;
    }

    public void setReceiver(Wallet wallet) {
        this.receiver = wallet;
    }

    public Wallet getSender() {
        return sender;
    }

    public Transaction sender(Wallet wallet) {
        this.sender = wallet;
        return this;
    }

    public void setSender(Wallet wallet) {
        this.sender = wallet;
    }

    public PassBook getPassbook() {
        return passbook;
    }

    public Transaction passbook(PassBook passBook) {
        this.passbook = passBook;
        return this;
    }

    public void setPassbook(PassBook passBook) {
        this.passbook = passBook;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", charges=" + getCharges() +
            "}";
    }
}
