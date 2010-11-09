package de.servicezombie.csv2qif.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class QuickenTransaction implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String category = null;

	private String currency = null;
	private Double amount = 0.0;
	private String partnerName = null;

	private Date bookingDate = null;
	private Date valueDate = null;

	private Integer accountNo = 0;
	private Integer bankNo = 0;

	private String text = null;
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public QuickenTransaction clone() {
		QuickenTransaction item = new QuickenTransaction();
		
		item.category = this.category;
		item.currency = this.currency;
		item.amount = this.amount;
		item.partnerName = this.partnerName;
		item.bookingDate = this.bookingDate;
		
		item.valueDate = this.valueDate;
		item.accountNo = this.accountNo;
		item.bankNo = this.bankNo;
		item.text = this.text;
		
		return item;
	}	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Integer getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}

	public Integer getBankNo() {
		return bankNo;
	}

	public void setBankNo(Integer bankNo) {
		this.bankNo = bankNo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
