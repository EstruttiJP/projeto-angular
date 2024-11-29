package br.com.estruttijp.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.estruttijp.data.vo.v2.ProductVO;
import br.com.estruttijp.exceptions.ResourceNotFoundException;
import br.com.estruttijp.mapper.DozerMapper;
import br.com.estruttijp.repositories.UserRepository;


@Service
public class UserServices implements UserDetailsService {
	
	private Logger logger = Logger.getLogger(UserServices.class.getName());
	
	@Autowired
	UserRepository repository;
	
	public UserServices(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");
		var user = repository.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
	}
	
	public List<ProductVO> getProductsInCart(Long userId) {
		logger.info("Finding products in Carts!");
		
        var entity = repository.findProductsInCartByUserId(userId);
        var products = DozerMapper.parseListObjects(entity, ProductVO.class);
        
        if (products != null) {
			return products;
		} else {
			throw new ResourceNotFoundException("No products found for this cart! It is empty!");
		}
    }
}