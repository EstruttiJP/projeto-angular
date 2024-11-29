package br.com.estruttijp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estruttijp.model.Product;
import br.com.estruttijp.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.userName =:userName")
	User findByUsername(@Param("userName") String userName);
	
	@Query("SELECT u.produtos FROM User u WHERE u.id = :userId")
    List<Product> findProductsInCartByUserId(@Param("userId") Long userId);
}