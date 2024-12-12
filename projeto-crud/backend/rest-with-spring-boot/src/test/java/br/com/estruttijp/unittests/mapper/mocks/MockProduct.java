package br.com.estruttijp.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.estruttijp.data.vo.v2.ProductVO;
import br.com.estruttijp.model.Product;

public class MockProduct {


    public Product mockEntity() {
        return mockEntity(0);
    }
    
    public ProductVO mockVO() {
        return mockVO(0);
    }
    
    public List<Product> mockEntityList() {
        List<Product> persons = new ArrayList<Product>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<ProductVO> mockVOList() {
        List<ProductVO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVO(i));
        }
        return persons;
    }
    
    public Product mockEntity(Integer number) {
        Product product = new Product();
        product.setName("Name Test" + number);
        product.setPrice(25D);
        product.setCategory("Category Test:" + number);;
        product.setId(number.longValue());
        product.setLaunchDate(new Date());
        product.setImageUrl("image/teste/"+number);
        return product;
    }

    public ProductVO mockVO(Integer number) {
    	 ProductVO product = new ProductVO();
         product.setName("Name Test" + number);
         product.setPrice(25D);
         product.setCategory("Category Test:" + number);;
         product.setKey(number.longValue());
         product.setLaunchDate(new Date());
         product.setImageUrl("image/teste/"+number);
         return product;
    }

}
