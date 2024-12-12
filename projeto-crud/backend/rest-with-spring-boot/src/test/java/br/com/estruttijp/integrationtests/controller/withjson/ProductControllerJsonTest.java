package br.com.estruttijp.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

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
import br.com.estruttijp.data.vo.v2.security.AccountCredentialsVO;
//import br.com.erudio.configs.TestConfigs;
//import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.estruttijp.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.estruttijp.integrationtests.vo.ProductVO;
//import br.com.estruttijp.integrationtests.vo.BookVO;
import br.com.estruttijp.integrationtests.vo.TokenVO;
import br.com.estruttijp.integrationtests.vo.wrappers.WrapperProductVO;
//import br.com.estruttijp.integrationtests.vo.wrappers.WrapperBookVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ProductControllerJsonTest extends AbstractIntegrationTest {

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
    @Order(1)
    public void authorization() {
        AccountCredentialsVO user = new AccountCredentialsVO();
        user.setUsername("joao");
        user.setPassword("admin123");

        var token =
                given()
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

            specification =
                new RequestSpecBuilder()
                    .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token)
                    .setBasePath("/api/product/v2")
                    .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                    .build();
    }
      
    @Test
    @Order(2)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        
        mockProduct();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
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
    @Order(3)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        
    	product.setName("Carne - Carne de Boi - Updated");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(product)
                    .when()
                    .put()
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();
        
        ProductVO productUpdated = objectMapper.readValue(content, ProductVO.class);
        
        assertNotNull(productUpdated.getId());
        assertNotNull(productUpdated.getName());
        assertNotNull(productUpdated.getCategory());
        assertNotNull(productUpdated.getImageUrl());
        assertNotNull(productUpdated.getPrice());
        assertEquals(productUpdated.getId(), product.getId());
        assertEquals("Carne - Carne de Boi - Updated", productUpdated.getName());
        assertEquals("Alimentos", productUpdated.getCategory());
        assertEquals("https://example.com/images/beef.jpg", productUpdated.getImageUrl());
        assertEquals(49.99, productUpdated.getPrice());
    }

    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParam("id", product.getId())
                    .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();
        
        ProductVO foundProduct = objectMapper.readValue(content, ProductVO.class);
        
        assertNotNull(foundProduct.getId());
        assertNotNull(foundProduct.getName());
        assertNotNull(foundProduct.getCategory());
        assertNotNull(foundProduct.getImageUrl());
        assertNotNull(foundProduct.getPrice());
        assertEquals(foundProduct.getId(), product.getId());
        assertEquals("Carne - Carne de Boi - Updated", foundProduct.getName());
        assertEquals("https://example.com/images/beef.jpg", foundProduct.getImageUrl());
        assertEquals("Alimentos", foundProduct.getCategory());
        assertEquals(49.99, foundProduct.getPrice());
    }
    
    @Test
    @Order(5)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParam("id", product.getId())
                    .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);
    }
    
    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
            	.queryParams("page", 0 , "limit", 12, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .asString();
        

        WrapperProductVO wrapper = objectMapper.readValue(content, WrapperProductVO.class);
        List<ProductVO> products = wrapper.getEmbedded().getProducts();

        ProductVO foundProductOne = products.get(0);
        
        assertNotNull(foundProductOne.getId());
        assertNotNull(foundProductOne.getName());
        assertNotNull(foundProductOne.getCategory());
        assertNotNull(foundProductOne.getImageUrl());
        assertNotNull(foundProductOne.getPrice());
        assertTrue(foundProductOne.getId() > 0);
        assertEquals("Abacate", foundProductOne.getName());
        assertEquals("Alimentos", foundProductOne.getCategory());
        assertEquals("https://example.com/images/avocado.jpg", foundProductOne.getImageUrl());
        assertEquals(4.0, foundProductOne.getPrice());
        
        ProductVO foundProductFive = products.get(4);
        
        assertNotNull(foundProductFive.getId());
        assertNotNull(foundProductFive.getName());
        assertNotNull(foundProductFive.getCategory());
        assertNotNull(foundProductFive.getImageUrl());
        assertNotNull(foundProductFive.getPrice());
        assertTrue(foundProductFive.getId() > 0);
        assertEquals("Carne - Carne de Vaca", foundProductFive.getName());
        assertEquals("Alimentos", foundProductFive.getCategory());
        assertEquals("https://example.com/images/beef.jpg", foundProductFive.getImageUrl());
        assertEquals(26.0, foundProductFive.getPrice());
    }
	
	@Test
	@Order(7)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
            	.queryParams("page", 0 , "size", 12, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .asString();
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/product/v2/12\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/product/v2/15\"}}}"));		
		assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8888/api/product/v2?direction=asc&page=0&size=12&sort=name,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/product/v2?page=0&size=12&direction=asc\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/product/v2?direction=asc&page=1&size=12&sort=name,asc\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/product/v2?direction=asc&page=1&size=12&sort=name,asc\"}}"));
		
		assertTrue(content.contains("\"page\":{\"size\":12,\"totalElements\":20,\"totalPages\":2,\"number\":0}}"));
	}
	
    private void mockProduct() {    	
    	product.setName("Carne - Carne de Boi");
    	product.setCategory("Alimentos");
    	product.setImageUrl("https://example.com/images/beef.jpg");
    	product.setPrice(Double.valueOf(49.99));
    	product.setLaunchDate(new Date());
    }    
}