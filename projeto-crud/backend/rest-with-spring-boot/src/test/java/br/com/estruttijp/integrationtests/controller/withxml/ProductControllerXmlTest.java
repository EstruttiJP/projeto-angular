package br.com.estruttijp.integrationtests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.estruttijp.configs.TestConfigs;
import br.com.estruttijp.data.vo.v2.security.TokenVO;
//import br.com.erudio.configs.TestConfigs;
//import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.estruttijp.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.estruttijp.integrationtests.vo.AccountCredentialsVO;
//import br.com.estruttijp.integrationtests.vo.BookVO;
import br.com.estruttijp.integrationtests.vo.ProductVO;
import br.com.estruttijp.integrationtests.vo.pagedmodels.PagedModelProduct;
import br.com.estruttijp.integrationtests.vo.wrappers.WrapperProductVO;
//import br.com.estruttijp.integrationtests.vo.pagedmodels.PagedModelBook;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ProductControllerXmlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static XmlMapper objectMapper;
    private static ProductVO product;

    @BeforeAll
    public static void setup() {
        objectMapper = new XmlMapper();
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
					.contentType(TestConfigs.CONTENT_TYPE_XML)
					.accept(TestConfigs.CONTENT_TYPE_XML)
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
    @Order(2)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        
        mockProduct();

        var content = given().spec(specification)
        		.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
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
        		.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
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
        		.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
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
        	.contentType(TestConfigs.CONTENT_TYPE_XML)
        	.accept(TestConfigs.CONTENT_TYPE_XML)
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
        		.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
            	.queryParams("page", 0 , "size", 12, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .asString();
        

        PagedModelProduct wrapper = objectMapper.readValue(content, PagedModelProduct.class);
        List<ProductVO> products = wrapper.getContent();

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
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.setBasePath("/api/product/v2")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		given().spec(specificationWithoutToken)
			.contentType(TestConfigs.CONTENT_TYPE_XML)
			.accept(TestConfigs.CONTENT_TYPE_XML)
				.when()
				.get()
			.then()
				.statusCode(200);
	}
	
    private void mockProduct() {    	
    	product.setName("Carne - Carne de Boi");
    	product.setCategory("Alimentos");
    	product.setImageUrl("https://example.com/images/beef.jpg");
    	product.setPrice(Double.valueOf(49.99));
    	product.setLaunchDate(new Date());
    }    
}