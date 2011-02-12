package org.springframework.data.mongodb.examples.hello.domain;

public class Account {

	public enum Type {
		SAVINGS, CHECKING
	}
	
	private String id;
	
	private String accountNumber;
	
	private Account.Type accountType;
	
	private Double balance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Account.Type getAccountType() {
		return accountType;
	}

	public void setAccountType(Account.Type accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
