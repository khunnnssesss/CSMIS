package com.dat.csmis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dat.csmis.entity.MeatList;

@Repository
public interface AvoidMSP extends JpaRepository<MeatList, Integer> {

	@Query("SELECT m FROM MeatList m where m.isDelete='Active'")
	List<MeatList> allList();

	@Query("SELECT DISTINCT m.meat FROM MeatList m where m.isDelete='Active'")
	List<String> getAvoidMeatDuplicate();


  Optional<MeatList> findById(int id);

  

}