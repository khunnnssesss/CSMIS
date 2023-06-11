package com.dat.csmis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dat.csmis.entity.MenuEntity;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

	@Query("update MenuEntity r set r.status= ?1 where r.id= ?2")
	@Modifying
	@Transactional
	void updateA(String active, int id);
	
	@Query("update MenuEntity r set r.status= ?1 where r.id < ?2")
	@Modifying
	@Transactional
	void updateB(String active, int id);
	
	@Query("select m from MenuEntity m where m.status='Active'")
	MenuEntity select();
}
