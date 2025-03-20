package com.victorio.finances.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victorio.finances.dto.TransactionDto;
import com.victorio.finances.enums.TypeEnum;
import com.victorio.finances.exceptions.EntityNotFoundException;
import com.victorio.finances.models.TransactionModel;
import com.victorio.finances.models.UserModel;
import com.victorio.finances.repositories.TransactionRepository;
import com.victorio.finances.repositories.UserRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserRepository userRepository;
	
	public List<TransactionModel> transactionByUser(String username) {
		UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User"));
		List<TransactionModel> transactionList = transactionRepository.findByUser(user);
		return transactionList;
	}
		
	public void saveTransaction(String username, TransactionDto transactionDto) {
		if(transactionDto.type() == TypeEnum.EXPENSE && transactionDto.value() > getTotalBalance(username)) {
			throw new RuntimeException("Amount to spend cannot be greater than the total amount.");
		}
		
		UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User"));
		TransactionModel transaction = new TransactionModel(
				transactionDto.value(),
				transactionDto.type(), 
				transactionDto.description(), 
				LocalDateTime.now(),
				user);

		user.addtransaction(transaction);
		userRepository.save(user);
	}
	
	public void deleteTransaction(Long transactionId) {
		transactionRepository.findById(transactionId).orElseThrow(() -> new EntityNotFoundException("Transaction"));
		transactionRepository.deleteById(transactionId);
	}
	
	public Double getTotalIncome(String username) {
		UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User"));
		List <TransactionModel> transactions = transactionRepository.findByTypeAndUser(TypeEnum.INCOME, user);
		return transactions.stream().mapToDouble(t -> t.getValue()).sum();
	}
	
	public Double getTotalExpense(String username) {
		UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User"));
		List <TransactionModel> transactions = transactionRepository.findByTypeAndUser(TypeEnum.EXPENSE, user);
		return transactions.stream().mapToDouble(t -> t.getValue()).sum();
	}
	
	public Double getTotalBalance(String username) {
		Double expenses = getTotalExpense(username);
		Double incomes = getTotalIncome(username);
		return incomes - expenses;
	}
	
}
