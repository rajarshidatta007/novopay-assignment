package com.assignment.service.dto;

import java.io.Serializable;
import java.util.Set;

import com.assignment.domain.Transaction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.assignment.domain.PassBook} entity.
 */
@ApiModel(description = "not an ignored comment")
public class PassBookDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Set<TransactionDTO> transactions;
    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassBookDTO)) {
            return false;
        }

        return id != null && id.equals(((PassBookDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PassBookDTO{" +
            "id=" + getId() +
            "}";
    }
}
