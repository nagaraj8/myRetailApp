package com.myretail.api.contoller;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.myretail.api.controller.ProductController;
import com.myretail.api.service.ProductServiceImpl;
import com.myretail.api.vo.Price;
import com.myretail.api.vo.Product;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ProductController.class)
@WebMvcTest(value=ProductController.class, secure = false)
public class ProductControllertTest {

	@Autowired
	private MockMvc mockMvc;
	
    @MockBean
    @Qualifier(value="productService")
    private  ProductServiceImpl productService;
	
    private int ID=13860428;

	Product prodDetails=null;
	Price prodPrice= new Price();
	@Before
	public void setup() {
		prodPrice.setId(ID);
		prodPrice.setCurrencyCode("USD");
		prodPrice.setPrice(new BigDecimal(2000));
		prodDetails= new Product(ID,"Kill Bill",prodPrice);
	}
	
	
	@Test
	public void getProductDetailsTest() throws Exception {
		Mockito.when(productService.getProduct(ID)).thenReturn(prodDetails);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/product/"+ID).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"id\":13860428,\"name\":\"Kill Bill\",\"productPrice\":{\"price\":2000,\"currencyCode\":\"USD\"}}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test(expected = MethodArgumentTypeMismatchException.class)
	public void getProductDetailsInvalidRequestTest() throws Exception,MethodArgumentTypeMismatchException {
		String var="XYZ";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/product/"+var).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
			throw 	result.getResolvedException();
	}
	
	@Test
	public void updateProductDetailsTest() throws Exception{
		Price prodPrice1= new Price();
		prodPrice1.setId(ID);
		prodPrice1.setCurrencyCode("Rupee");
		prodPrice1.setPrice(new BigDecimal(300));
		Product prodDetails1= new Product(ID,"Kill Bill",prodPrice1);
		
		Mockito.when(productService.updateProduct(ID, prodDetails1)).thenReturn(prodDetails1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/product/"+ID)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":"+ID+",\"name\":\"Kill Bill\",\"productPrice\":{\"price\":300,\"currencyCode\":\"Rupee\"}}")
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
	}
	
	@Test
	public void addProductDetailsTest() throws Exception{
		Price prodPrice1= new Price();
		prodPrice1.setId(ID);
		prodPrice1.setCurrencyCode("Rupee");
		prodPrice1.setPrice(new BigDecimal(300));
		Product prodDetails1= new Product(ID,"Kill Bill",prodPrice1);
		
		Mockito.when(productService.addProduct(ID, prodDetails1)).thenReturn(prodDetails1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/product/"+ID)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":"+ID+",\"name\":\"Kill Bill\",\"productPrice\":{\"price\":300,\"currencyCode\":\"Rupee\"}}")
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
	}
}
