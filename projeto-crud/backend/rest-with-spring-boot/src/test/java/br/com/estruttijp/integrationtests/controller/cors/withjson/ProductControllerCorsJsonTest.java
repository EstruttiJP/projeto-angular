package br.com.estruttijp.integrationtests.controller.cors.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.estruttijp.configs.TestConfigs;
import br.com.estruttijp.data.vo.v2.security.TokenVO;
import br.com.estruttijp.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.estruttijp.integrationtests.vo.AccountCredentialsVO;
import br.com.estruttijp.integrationtests.vo.ProductVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ProductControllerCorsJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static ProductVO product;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        product = new ProductVO();
    }
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("joao", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/product/v2")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockProduct();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                	.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ANGULAR)
                    .body(product)
                    .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();
        
        product = objectMapper.readValue(content, ProductVO.class);
        
        assertNotNull(product.getId());
        assertNotNull(product.getName());
        assertNotNull(product.getCategory());
        assertNotNull(product.getImageUrl());
        assertNotNull(product.getPrice());
        assertTrue(product.getId() > 0);
        assertEquals("Carne - Carne de Boi", product.getName());
        assertEquals("Alimentos", product.getCategory());
        assertEquals("https://example.com/images/beef.jpg", product.getImageUrl());
        assertEquals(49.99, product.getPrice());
	}

	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockProduct();
	
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
					.body(product)
				.when()
					.post()
				.then()
					.statusCode(403)
						.extract()
							.body()
								.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockProduct();
			
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ANGULAR)
					.pathParam("id", product.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		ProductVO persistedProduct = objectMapper.readValue(content, ProductVO.class);
		product = persistedProduct;
        
		assertNotNull(persistedProduct);
		
		assertNotNull(persistedProduct.getId());
        assertNotNull(persistedProduct.getName());
        assertNotNull(persistedProduct.getCategory());
        assertNotNull(persistedProduct.getImageUrl());
        assertNotNull(persistedProduct.getPrice());
        assertTrue(persistedProduct.getId() > 0);
        assertEquals("Carne - Carne de Boi", persistedProduct.getName());
        assertEquals("Alimentos", persistedProduct.getCategory());
        assertEquals("https://example.com/images/beef.jpg", persistedProduct.getImageUrl());
        assertEquals(49.99, persistedProduct.getPrice());
	}
	

	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockProduct();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
					.pathParam("id", product.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(403)
						.extract()
						.body()
							.asString();

		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(5)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", product.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	private void mockProduct() {    	
		product.setName("Carne - Carne de Boi");
    	product.setCategory("Alimentos");
    	product.setImageUrl("https://example.com/images/beef.jpg");
    	product.setPrice(Double.valueOf(49.99));
    	product.setLaunchDate(new Date());
    }  

}
