package br.com.estruttijp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.estruttijp.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.name LIKE LOWER(CONCAT ('%',:name,'%'))")
	Page<Product> findProductsByName(@Param("name") String name, Pageable pageable);
	
}