package br.com.estruttijp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.estruttijp.data.vo.v2.ProductVO;
import br.com.estruttijp.services.ProductServices;
import br.com.estruttijp.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/product/v2")
@Tag(name = "Product", description = "Endpoints for Managing Products")
public class ProductController {
	
	@Autowired
	private ProductServices service;
	
	@GetMapping(
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds all Products", description = "Finds all Products",
		tags = {"Product"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = ProductVO.class))
					)
				}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<PagedModel<EntityModel<ProductVO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction)
				? Direction.DESC : Direction.ASC;
			
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(service.findAll(pageable));
	}
		
	@GetMapping(value = "/findProductByName/{name}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds Product by Name", description = "Finds Product by Name",
	tags = {"Product"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = ProductVO.class))
									)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<PagedModel<EntityModel<ProductVO>>> findProductByName(
			@PathVariable(value = "name") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction)
				? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(service.findProductByName(name, pageable));
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}",
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Finds a Product", description = "Finds a Product",
		tags = {"Product"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = ProductVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ProductVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Adds a new Product",
		description = "Adds a new Product by passing in a JSON, XML or YML representation of the product!",
		tags = {"Product"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = ProductVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ProductVO create(@RequestBody ProductVO productVO) {
		return service.create(productVO);
	}
	
	@PutMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Updates a Product",
		description = "Updates a Product by passing in a JSON, XML or YML representation of the product!",
		tags = {"Product"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
				content = @Content(schema = @Schema(implementation = ProductVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ProductVO update(@RequestBody ProductVO productVO) {
		return service.update(productVO);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Product",
		description = "Deletes a Product by passing in a JSON, XML or YML representation of the product!",
		tags = {"Product"},
		responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
