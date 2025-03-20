package com.victorio.finances.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.victorio.finances.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
	Optional<UserModel> findByUsername(String username);
}
