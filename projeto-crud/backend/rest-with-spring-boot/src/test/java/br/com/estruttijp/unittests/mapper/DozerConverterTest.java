package br.com.estruttijp.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.estruttijp.data.vo.v2.ProductVO;
import br.com.estruttijp.mapper.DozerMapper;
import br.com.estruttijp.model.Product;
import br.com.estruttijp.unittests.mapper.mocks.MockProduct;

public class DozerConverterTest {
    
    MockProduct inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockProduct();
    }

    @Test
    public void parseEntityToVOTest() {
        ProductVO output = DozerMapper.parseObject(inputObject.mockEntity(), ProductVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
		assertEquals("Name Test0", output.getName());
		assertEquals(25D, output.getPrice());
		assertEquals("Category Test:0", output.getCategory());
		assertEquals("image/teste/0", output.getImageUrl());
		assertNotNull(output.getLaunchDate());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<ProductVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), ProductVO.class);
        ProductVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
		assertEquals("Name Test0", outputZero.getName());
		assertEquals(25D, outputZero.getPrice());
		assertEquals("Category Test:0", outputZero.getCategory());
		assertEquals("image/teste/0", outputZero.getImageUrl());
		assertNotNull(outputZero.getLaunchDate());
        
        ProductVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
		assertEquals("Name Test7", outputSeven.getName());
		assertEquals(25D, outputSeven.getPrice());
		assertEquals("Category Test:7", outputSeven.getCategory());
		assertEquals("image/teste/7", outputSeven.getImageUrl());
		assertNotNull(outputSeven.getLaunchDate());
        
        ProductVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("Name Test12", outputTwelve.getName());
		assertEquals(25D, outputTwelve.getPrice());
		assertEquals("Category Test:12", outputTwelve.getCategory());
		assertEquals("image/teste/12", outputTwelve.getImageUrl());
		assertNotNull(outputTwelve.getLaunchDate());
        
    }

    @Test
    public void parseVOToEntityTest() {
        Product output = DozerMapper.parseObject(inputObject.mockVO(), Product.class);
        assertEquals(Long.valueOf(0L), output.getId());
		assertEquals("Name Test0", output.getName());
		assertEquals(25D, output.getPrice());
		assertEquals("Category Test:0", output.getCategory());
		assertEquals("image/teste/0", output.getImageUrl());
		assertNotNull(output.getLaunchDate());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Product> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Product.class);
        Product outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
		assertEquals("Name Test0", outputZero.getName());
		assertEquals(25D, outputZero.getPrice());
		assertEquals("Category Test:0", outputZero.getCategory());
		assertEquals("image/teste/0", outputZero.getImageUrl());
		assertNotNull(outputZero.getLaunchDate());
        
        Product outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Name Test7", outputSeven.getName());
		assertEquals(25D, outputSeven.getPrice());
		assertEquals("Category Test:7", outputSeven.getCategory());
		assertEquals("image/teste/7", outputSeven.getImageUrl());
		assertNotNull(outputSeven.getLaunchDate());
        
        Product outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Name Test12", outputTwelve.getName());
		assertEquals(25D, outputTwelve.getPrice());
		assertEquals("Category Test:12", outputTwelve.getCategory());
		assertEquals("image/teste/12", outputTwelve.getImageUrl());
		assertNotNull(outputTwelve.getLaunchDate());
    }
}
