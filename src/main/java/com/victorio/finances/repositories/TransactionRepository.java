package com.victorio.finances.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorio.finances.enums.TypeEnum;
import com.victorio.finances.models.TransactionModel;
import com.victorio.finances.models.UserModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long>{
	List<TransactionModel> findByUser(UserModel user);
	List<TransactionModel> findByTypeAndUser(TypeEnum type, UserModel user);
}
