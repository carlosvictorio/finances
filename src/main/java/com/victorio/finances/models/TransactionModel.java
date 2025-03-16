package com.victorio.finances.models;

import java.time.LocalDateTime;

import com.victorio.finances.enums.TypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "transactions")
public class TransactionModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "insert a valid value!")
	@DecimalMin(value = "0.0", message = "The value can't be lower than 0!")
	private Double value;
	@NotBlank(message = "The type can't be empty!")
	private TypeEnum type;
	private String description;
	@NotBlank(message = "insert a valid date!")
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserModel user;
	
	public TransactionModel() {
		
	}

	public TransactionModel( Double value, TypeEnum type,  String description, LocalDateTime date) {
		this.value = value;
		this.type = type;
		this.description = description;
		this.date = date;
	}
	
	public Long getId() {
		return id;
	}

	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}
