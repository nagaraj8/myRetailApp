package com.myretail.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Target design challenge option 1
 * Author: Gaurav Karale
 * version 1.0.0
 * Date :- 05/24/2017
 * Info :-ProductPriceRepository (Mongo data repository) for collection productprice
 * */

import com.myretail.api.vo.Price;
public interface ProductPriceRepository extends MongoRepository<Price, Integer> {
	public Price findById(int id);
}
