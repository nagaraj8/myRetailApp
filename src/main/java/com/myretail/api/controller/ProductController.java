package com.myretail.api.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.mongodb.MongoException;
import com.myretail.api.service.ProductService;
import com.myretail.api.vo.Product;

/**
 * Target Case Study
 * @author Nagaraj Hegde
 *
 */
@RestController
@RequestMapping("/")
public class ProductController {

	private final Logger log = Logger.getLogger(ProductController.class.getName());
	
	@Autowired
	@Qualifier(value="productService")
	ProductService productService;
	
	
	@RequestMapping(value="product/{id}",method=RequestMethod.GET)
	public Product getProduct(@PathVariable int id) throws HttpClientErrorException, MongoException, IOException{
		log.info("Product id :"+id);
		Product product=null;
		product=productService.getProduct(id);
		log.info("Fetched product Details :"+product);
		return product;
	}
	
	@RequestMapping(value="product/{id}",method=RequestMethod.PUT)
	public Product updateProduct(@PathVariable int id,@RequestBody Product product) throws Exception{
		log.info("Product id :"+id);
		log.info("Product Details :"+product);
		Product updatedProduct=productService.updateProduct(id, product);
		log.info(" updated product Details:"+updatedProduct);
		return updatedProduct;
	}
	
	@RequestMapping(value="product/{id}",method=RequestMethod.POST)
	public Product addProduct(@PathVariable int id,@RequestBody Product product) throws Exception{
		log.info("Product id :"+id);
		log.info("Product requestBody :"+product);
		Product addProduct=productService.addProduct(id, product);
		log.info(" added product Details :"+addProduct);
		return addProduct;
	}
}
