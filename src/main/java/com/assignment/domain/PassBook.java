package com.assignment.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "pass_book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PassBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * A relationship
     */
    @OneToMany(mappedBy = "passbook")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public PassBook transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public PassBook addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setPassbook(this);
        return this;
    }

    public PassBook removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setPassbook(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassBook)) {
            return false;
        }
        return id != null && id.equals(((PassBook) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PassBook{" +
            "id=" + getId() +
            "}";
    }
}
