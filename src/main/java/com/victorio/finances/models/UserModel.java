package com.victorio.finances.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.victorio.finances.dto.UserDto;
import com.victorio.finances.enums.RoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserModel implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	@Column(unique = true)
	@NotBlank(message = "The username can't be empty!")
	private String username;
	@NotBlank(message = "The password can't be empty!")
	private String password;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TransactionModel> transactions;
	
	public UserModel() {
	}
	
	public UserModel(String username, String password) {
		this.role = RoleEnum.USER;
		this.username = username;
		this.password = password;
	}
	
	public UserModel(UserDto user) {
		this.role = RoleEnum.USER;
		this.username = user.username();
		this.password = user.password();
	}
	
	public Long getId() {
		return id;
	}
	
	public RoleEnum getRole() {
		return role;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<TransactionModel> getTransactions() {
		return transactions;
	}
	
	public void addtransaction(TransactionModel transaction) {
		transactions.add(transaction);
	}
	
	public void removeTransaction(TransactionModel transaction){
		transactions.remove(transaction);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == RoleEnum.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
}
