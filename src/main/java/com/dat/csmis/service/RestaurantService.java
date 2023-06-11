package com.dat.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.PriceEntity;
import com.dat.csmis.entity.RestaurantEntity;
import com.dat.csmis.repository.PriceRepository;
import com.dat.csmis.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepository restaurantRepo;
	@Autowired
	PriceRepository Prepo;
	
	public void save(RestaurantEntity entity) {
		restaurantRepo.save(entity);
	}
	public int count() {
		return (int) restaurantRepo.count();
	}
	public void updateA(String active, int id) {
		restaurantRepo.updateA(active, id);
	}
	public void saveP(PriceEntity entity) {
		Prepo.save(entity);
	}
	public void updateB(String InActive, int id) {
		restaurantRepo.updateB(InActive, id);
	}
	public void updateC(String InActive, int id) {
		restaurantRepo.updateB(InActive, id);
	}

	public RestaurantEntity getPriceByRestaurant(){
		RestaurantEntity restaurantPrice=restaurantRepo.priceByRestaurant();
		return restaurantPrice;
	}
	public List<RestaurantEntity> selectAll(){
		return restaurantRepo.selectAll();
	}
	public Optional<RestaurantEntity> findById(int id) {
		return restaurantRepo.findById(id);
	}
	public Optional<PriceEntity> findByIdForPrice(int id){
		return Prepo.findById(id);
	}
	public void save(RestaurantEntity entity, int id) {
		restaurantRepo.save(entity);
	}
	
}
