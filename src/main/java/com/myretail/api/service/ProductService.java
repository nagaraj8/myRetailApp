package com.myretail.api.service;

import java.io.IOException;

import org.springframework.web.client.HttpClientErrorException;

import com.mongodb.MongoException;
import com.myretail.api.vo.Product;

/**
 * Target Case Study
 * @author Nagaraj Hegde
 *
 */
public interface ProductService {

	public Product getProduct(int id) throws MongoException,MongoException, HttpClientErrorException,IOException;
	public Product updateProduct(int id,Product newProduct) throws Exception;
	public Product addProduct(int id,Product newProduct) throws Exception;
}
