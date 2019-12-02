package com.myretail.api.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.myretail.api.repository.ProductPriceRepository;
import com.myretail.api.vo.Price;
import com.myretail.api.vo.Product;

/**
 * Target Case Study
 * @author Nagaraj Hegde
 *
 */
@Service(value="productService")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ProductServiceImpl implements ProductService {
	private final Logger log = Logger.getLogger(ProductServiceImpl.class.getName());
	
	@Autowired
	private ProductPriceRepository productPriceRepository;
	@Autowired
	private ProductServiceImpl productServiceImpl;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Environment env;
	
	
	@Override
	public Product getProduct(int id) throws MongoException,IOException{
		log.debug("id: "+id);
		String productName=getProductName(id);
		log.debug("productName: "+productName);
		Price prodPrice=productServiceImpl.getProductPrice(id);
		if(prodPrice==null){
			log.error("price detail null mongo exception thrown");
			throw new MongoException("price details for product with id="+id+" is not found in the database");
		}
		Product prodDetails= new Product(id,productName,prodPrice);
		log.debug("prodDetails: "+prodDetails);
		return prodDetails;
	}

	@Override
	public Product updateProduct(int id, Product newProduct) throws Exception{
		log.debug(" newProduct : "+newProduct);
		if(id!=newProduct.getId()){
			throw new Exception(" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		Price newProductPrice=newProduct.getPrice();
		if(newProduct.getPrice().getCurrencyCode()==null||newProduct.getPrice().getPrice()==null){
				throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		String productName=getProductName(id);
		newProduct.setName(productName);
		newProductPrice=productServiceImpl.updateProductPrice(id,newProduct);
		
		newProduct.setPrice(newProductPrice);
		return newProduct;
	}
	
	@Cacheable(value = "productPriceCache", key = "#id")
	public Price getProductPrice(int id) throws MongoException{
		log.debug("id : "+id);
		Price prodPrice=productPriceRepository.findById(id);
		log.debug("prodPrice : "+prodPrice);
		return prodPrice;
	}
	
	
	@CachePut(value = "productPriceCache", key = "#id")
	public Price updateProductPrice(int id,Product newProduct) throws MongoException{
		log.info("in updateProductPrice");
		Price newProductPrice=newProduct.getPrice();
		newProductPrice.setId(id);
		if(productPriceRepository.findById(newProductPrice.getId())!=null){
			newProductPrice=productPriceRepository.save(newProduct.getPrice());
		}else{
			log.error("price detail null mongo exception thrown");
			throw new MongoException("price details for product with id="+id+" not found in mongo db for collection 'productprice'");
		}
		log.debug("newProductPrice : "+newProductPrice);
		return newProductPrice;
	}
	
	private String getProductName(int id) throws IOException{
		log.info("in getProductName");
		String url=	env.getProperty("target.restUrl1")+id+env.getProperty("target.restUrl2");
		ResponseEntity<String> response= restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		String productName="";
		try {
			JsonNode root=null;
			String jsonString=response.getBody();
			if(jsonString!=null||!"".equals(jsonString)){
				root = mapper.readTree(jsonString);
				if(root.findValue("product")!=null){
					root=root.findValue("product");
					if(root.findValue("item")!=null){
						root=root.findValue("item");
						if(root.findValue("product_description")!=null){
							root=root.findValue("product_description");
							if(root.findValue("title")!=null){
								productName=root.findValue("title").asText();
							}
						}
					}
				}
			}
			log.debug("productName : "+productName);
		} 
		catch (IOException e) {
			log.error("Parsing failed IOException "+e.getMessage());
			throw new IOException(e.getMessage());
		}
		return productName;
	}
	
	
	@CachePut(value = "productPriceCache", key = "#id")
	public Price saveProductPrice(int id,Product newProduct) throws MongoException{
		log.info("in saveProductPrice");
		Price newProductPrice=newProduct.getPrice();
		newProductPrice.setId(id);
		if(productPriceRepository.findById(newProductPrice.getId())==null){
			newProductPrice=productPriceRepository.save(newProduct.getPrice());
		}else{
			log.error("price detail null mongo exception thrown");
			throw new MongoException("price details for product with id="+id+" already exists in the database");
		}
		log.debug("newProductPrice : "+newProductPrice);
		return newProductPrice;
	}
	
	
	@Override
	public Product addProduct(int id, Product newProduct) throws Exception {
		log.info("in addProductDetails");
		log.debug(" newProduct : "+newProduct);
		if(id == newProduct.getId()){
			throw new Exception(" Product cannot be added since the product already exists.");
		}
		Price newProductPrice=newProduct.getPrice();
		if(newProduct.getPrice().getCurrencyCode()==null||newProduct.getPrice().getPrice()==null){
				throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		String productName=getProductName(id);
		newProduct.setName(productName);
		newProductPrice=productServiceImpl.saveProductPrice(id,newProduct);
		
		newProduct.setPrice(newProductPrice);
		return newProduct;
	}

}
