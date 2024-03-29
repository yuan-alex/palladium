/*
Name:         GiftCard.java
Author:       Alex Yuan
Date:         Jan 18, 2020
Purpose:      This class is responsible for representing
              the GiftCard in class form. This includes storing
              all the varibles and applying various methods.
*/

public class GiftCard extends Card {
	private String retailer;
	private double balance;

	public GiftCard(String name, String retailer, int number, double balance, int pin, DateTime expiryDate) {
		super(name, number, pin, expiryDate);
		this.balance = balance;
		this.retailer = retailer;
	}

	// ********** GETTERS AND SETTERS **********
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRetailer() {
		return this.retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getPin() {
		return this.pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public DateTime getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(DateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	// Allows the user to compare to another GiftCard object
	public double compareTo(GiftCard other) {
		return this.balance - other.balance;
	}

	public String toString() {
		return this.name + "\n" + this.retailer + "\n" + this.number + "\n" + this.balance + "\n" + this.pin + "\n"
				+ this.expiryDate.toString();
	}

}