package com.dat.csmis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dat.csmis.entity.RestaurantEntity;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

	// boolean existsByEmail(String email);
	boolean existsByPhone(String phone);

	//
	@Query("select r from RestaurantEntity r where r.email<>''")
	List<RestaurantEntity> findAllByEmailNotNull();
	//

	
	
	@Query("update RestaurantEntity r set r.status= ?1 where r.id= ?2")
	@Modifying
	@Transactional
	void updateA(String active, int id);
	
	@Query("update RestaurantEntity r set r.status= ?1 where r.id != ?2")
	@Modifying
	@Transactional
	void updateB(String active, int id);
	
	@Query("update RestaurantEntity r set r.status= ?1 where r.id < ?2")
	@Modifying
	@Transactional
	void updateC(String active, int id);
	
	@Query("select r from RestaurantEntity r where r.status='Active'")
	  RestaurantEntity priceByRestaurant();
	
	@Query("select r from RestaurantEntity r")
	List<RestaurantEntity> selectAll();
}
