package br.com.estruttijp.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.estruttijp.data.vo.v2.ProductVO;
import br.com.estruttijp.exceptions.RequiredObjectIsNullException;
import br.com.estruttijp.model.Product;
import br.com.estruttijp.repositories.ProductRepository;
import br.com.estruttijp.services.ProductServices;
import br.com.estruttijp.unittests.mapper.mocks.MockProduct;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ProductServicesTest {

	MockProduct input;
	
	@InjectMocks
	private ProductServices service;
	
	@Mock
	ProductRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockProduct();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Product entity = input.mockEntity(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/product/v2/1>;rel=\"self\"]"));
		assertEquals("Name Test1", result.getName());
		assertEquals(25D, result.getPrice());
		assertEquals("Category Test:1", result.getCategory());
		assertEquals("image/teste/1", result.getImageUrl());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void testCreate() {
		Product entity = input.mockEntity(1); 
		entity.setId(1L);
		
		Product persisted = entity;
		persisted.setId(1L);
		
		ProductVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/product/v2/1>;rel=\"self\"]"));
		assertEquals("Name Test1", result.getName());
		assertEquals(25D, result.getPrice());
		assertEquals("Category Test:1", result.getCategory());
		assertEquals("image/teste/1", result.getImageUrl());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}


	@Test
	void testUpdate() {
		Product entity = input.mockEntity(1); 
		
		Product persisted = entity;
		persisted.setId(1L);
		
		ProductVO vo = input.mockVO(1);
		vo.setKey(1L);
		

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/product/v2/1>;rel=\"self\"]"));
		assertEquals("Name Test1", result.getName());
		assertEquals(25D, result.getPrice());
		assertEquals("Category Test:1", result.getCategory());
		assertEquals("image/teste/1", result.getImageUrl());
		assertNotNull(result.getLaunchDate());
	}
	

	
	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testDelete() {
		Product entity = input.mockEntity(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
}