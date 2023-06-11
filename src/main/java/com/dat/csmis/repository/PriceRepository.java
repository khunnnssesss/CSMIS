package com.dat.csmis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.PriceEntity;

public interface PriceRepository extends JpaRepository<PriceEntity, Integer>{
	
}
