package br.com.estruttijp.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.estruttijp.controller.ProductController;
import br.com.estruttijp.data.vo.v2.ProductVO;
import br.com.estruttijp.exceptions.RequiredObjectIsNullException;
import br.com.estruttijp.exceptions.ResourceNotFoundException;
import br.com.estruttijp.mapper.DozerMapper;
import br.com.estruttijp.model.Product;
import br.com.estruttijp.repositories.ProductRepository;

@Service
public class ProductServices {
	
	private Logger logger = Logger.getLogger(ProductServices.class.getName());
	
	@Autowired
	ProductRepository repository;
	
	@Autowired
	PagedResourcesAssembler<ProductVO> assembler;

	public PagedModel<EntityModel<ProductVO>> findAll(Pageable pageable) {

		logger.info("Finding all Products!");
		
		var productPage = repository.findAll(pageable);

		var productVosPage = productPage.map(p -> DozerMapper.parseObject(p, ProductVO.class));
		productVosPage.map(
			p -> p.add(
				linkTo(methodOn(ProductController.class)
					.findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(
			methodOn(ProductController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(),
						"asc")).withSelfRel();
		
		return assembler.toModel(productVosPage, link);
	}
	
	public PagedModel<EntityModel<ProductVO>> findProductByName(String name, Pageable pageable) {
		
		logger.info("Finding all products!");
		
		var productPage = repository.findProductsByName(name, pageable);
		
		var productVosPage = productPage.map(p -> DozerMapper.parseObject(p, ProductVO.class));
		productVosPage.map(
				p -> p.add(
						linkTo(methodOn(ProductController.class)
								.findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(ProductController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(),
						"asc")).withSelfRel();
		
		return assembler.toModel(productVosPage, link);
	}

	public ProductVO findById(Long id) {
		
		logger.info("Finding one product!");
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, ProductVO.class);
		vo.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public ProductVO create(ProductVO product) {

		if (product == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one product!");
		var entity = DozerMapper.parseObject(product, Product.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), ProductVO.class);
		vo.add(linkTo(methodOn(ProductController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public ProductVO update(ProductVO product) {

		if (product == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one product!");
		
		var entity = repository.findById(product.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setName(product.getName());
		entity.setPrice(product.getPrice());
		entity.setCategory(product.getCategory());
		entity.setLaunchDate(product.getLaunchDate());
		entity.setImageUrl(product.getImageUrl());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), ProductVO.class);
		vo.add(linkTo(methodOn(ProductController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one product!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
