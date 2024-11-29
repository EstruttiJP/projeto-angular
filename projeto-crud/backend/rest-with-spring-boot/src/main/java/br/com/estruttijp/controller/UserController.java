package br.com.estruttijp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estruttijp.data.vo.v2.ProductVO;
import br.com.estruttijp.services.UserServices;
import br.com.estruttijp.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart/v2")
@Tag(name = "Carts", description = "Endpoints for Managing Carts")
public class UserController {

	@Autowired
    private UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}",
    		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    	@Operation(summary = "Find products in Carts", description = "Finds products in Carts",
    		tags = {"Carts"},
    		responses = {
    			@ApiResponse(description = "Success", responseCode = "200",
    				content = {
    					@Content(
    						mediaType = "application/json",
    						array = @ArraySchema(schema = @Schema(implementation = ProductVO.class))
    					)
    				}),
    			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
    			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
    		}
    	)
    public ResponseEntity<List<ProductVO>> getProductsInCart(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProductsInCart(userId));
    }
    
    
}
