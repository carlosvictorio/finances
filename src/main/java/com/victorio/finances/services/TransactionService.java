package com.victorio.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.victorio.finances.dto.TransactionDto;
import com.victorio.finances.enums.TypeEnum;
import com.victorio.finances.models.TransactionModel;
import com.victorio.finances.models.UserModel;
import com.victorio.finances.repositories.TransactionRepository;
import com.victorio.finances.repositories.UserRepository;

@Service
public class TransactionService {
	
	private TransactionRepository transactionRepository;
	private UserRepository userRepository;
	
	List<TransactionModel> transactionByUser(Long userId) {
		userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
		List<TransactionModel> transactionList = transactionRepository.findByUser(userId);
		return transactionList;
	}
	
	void saveTransaction(TransactionDto transactionData) {
		TransactionModel transaction = new TransactionModel(transactionData.value(), transactionData.type(), transactionData.description(), transactionData.date());
		transactionRepository.save(transaction);
	}
	
	void deleteTransaction(Long userId) {
		transactionRepository.deleteById(userId);
	}
	
	Double getTotalIncome(Long userId) {
		UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
		List <TransactionModel> transactions = transactionRepository.findByTypeAndUser(TypeEnum.INCOME, user);
		return transactions.stream().mapToDouble(t -> t.getValue()).sum();
	}
	
	Double getTotalExpense(Long userId) {
		UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
		List <TransactionModel> transactions = transactionRepository.findByTypeAndUser(TypeEnum.EXPENSE, user);
		return transactions.stream().mapToDouble(t -> t.getValue()).sum();
	}
	
	Double getTotalBalance(Long userId) {
		Double expenses = getTotalExpense(userId);
		Double incomes = getTotalIncome(userId);
		return incomes - expenses;
	}
}
