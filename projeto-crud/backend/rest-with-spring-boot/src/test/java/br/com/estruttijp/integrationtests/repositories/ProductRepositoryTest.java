package br.com.estruttijp.integrationtests.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

//import br.com.erudio.model.Person;
//import br.com.erudio.repositories.PersonRepository;
import br.com.estruttijp.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.estruttijp.model.Product;
import br.com.estruttijp.repositories.ProductRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ProductRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	public ProductRepository repository;
	
	private static Product product;
	
	@BeforeAll
	public static void setup() {
		product = new Product();
	}
	
	@Test
	@Order(1)
	public void testFindByName() throws JsonMappingException, JsonProcessingException {
		
		Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "name"));
		product = repository.findProductsByName("Carne", pageable).getContent().get(0);
		
		assertNotNull(product.getId());
		assertNotNull(product.getName());
		assertNotNull(product.getPrice());
		assertNotNull(product.getCategory());
		assertNotNull(product.getLaunchDate());
		assertNotNull(product.getImageUrl());
		
		assertEquals(1, product.getId());
		
		assertEquals("Carne - Carne de Vaca", product.getName());
		assertEquals(26.0, product.getPrice());
		assertEquals("Alimentos", product.getCategory());
		assertEquals("assets/not-found.jpg", product.getImageUrl());
	}
}