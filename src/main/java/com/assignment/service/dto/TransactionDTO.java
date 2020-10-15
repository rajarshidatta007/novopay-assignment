package com.assignment.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the {@link com.assignment.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private BigDecimal amount;

    private BigDecimal charges;

	private Long receiverId;

    private Long senderId;
    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")

    private Long passbookId;
    
    private Party deductionParty;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long walletId) {
        this.receiverId = walletId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long walletId) {
        this.senderId = walletId;
    }

    public Long getPassbookId() {
        return passbookId;
    }

    public void setPassbookId(Long passBookId) {
        this.passbookId = passBookId;
    }

    public Party getDeductionParty() {
		return deductionParty;
	}

	public void setDeductionParty(Party deductionParty) {
		this.deductionParty = deductionParty;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", charges=" + getCharges() +
            ", receiverId=" + getReceiverId() +
            ", senderId=" + getSenderId() +
            ", passbookId=" + getPassbookId() +
            "}";
    }
}
