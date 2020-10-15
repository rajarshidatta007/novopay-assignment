package com.assignment.service.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "add money dto")
public class AddMoneyDto {
	
	private BigDecimal amount;

    private Long walletId;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
}
