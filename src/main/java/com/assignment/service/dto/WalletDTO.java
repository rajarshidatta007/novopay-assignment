package com.assignment.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.assignment.domain.Wallet} entity.
 */
public class WalletDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private BigDecimal balance;

    private Long owenerId;


    private Long passbookId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getOwenerId() {
        return owenerId;
    }

    public void setOwenerId(Long owenerId) {
        this.owenerId = owenerId;
    }

    public Long getPassbookId() {
        return passbookId;
    }

    public void setPassbookId(Long passBookId) {
        this.passbookId = passBookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalletDTO)) {
            return false;
        }

        return id != null && id.equals(((WalletDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalletDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", owenerId=" + getOwenerId() +
            ", passbookId=" + getPassbookId() +
            "}";
    }
}
