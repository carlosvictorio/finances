package com.victorio.finances.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.finances.dto.TransactionDto;
import com.victorio.finances.models.TransactionModel;
import com.victorio.finances.models.UserModel;
import com.victorio.finances.repositories.TransactionRepository;
import com.victorio.finances.repositories.UserRepository;
import com.victorio.finances.services.TransactionService;

@RequestMapping("/user/{username}/transactions")
@RestController
public class UserTransactionsController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping
	public ResponseEntity<List<TransactionModel>> getAllTransactions(@PathVariable String username) {
		UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException());
		List<TransactionModel> transactions = transactionRepository.findByUser(user);
		return ResponseEntity.status(200).body(transactions);
	}
	
	@PostMapping
	public ResponseEntity<String> addTransactions(@PathVariable String username, @RequestBody TransactionDto transaction) {
		transactionService.saveTransaction(username, transaction);
		return ResponseEntity.status(201).body("Transaction created successfully!");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeTransaction(@PathVariable Long id) {
		transactionService.deleteTransaction(id);
		return ResponseEntity.status(200).body("Transaction removed successufully!");
	}
	
	@GetMapping("/incomes")
	public ResponseEntity<Double> getTotalIncomes(@PathVariable String username) {
		Double totalIncomes = transactionService.getTotalIncome(username);
		return ResponseEntity.status(200).body(totalIncomes);
	}
	
	@GetMapping("/expenses")
	public ResponseEntity<Double> getTotalExpenses(@PathVariable String username) {
		Double totalExpenses = transactionService.getTotalExpense(username);
		return ResponseEntity.status(200).body(totalExpenses);
	}
	
	@GetMapping("/balance")
	public ResponseEntity<Double> getTotalBalance(@PathVariable String username) {
		Double totalBalance = transactionService.getTotalBalance(username);
		return ResponseEntity.status(200).body(totalBalance);
	}
	
}
