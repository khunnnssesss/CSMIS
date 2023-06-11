package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.WeeklyOrderEntity;

public interface WeeklyOrderRepository extends JpaRepository<WeeklyOrderEntity,Integer>{
	@Query("SELECT o FROM WeeklyOrderEntity o")
	  public Page<WeeklyOrderEntity> findAll(Pageable pageable);
	  
	  @Query("SELECT o from WeeklyOrderEntity o WHERE o.date between ?1 and ?2")
	  public Page<WeeklyOrderEntity> findAllO(Pageable pageable,String start,String end);
	  
	  @Query("SELECT o from WeeklyOrderEntity o WHERE o.date between ?1 and ?2")
	  public List<WeeklyOrderEntity> searchEngineReport(String start,String end);
	  
	  @Query("SELECT o FROM WeeklyOrderEntity o")
	  public List<WeeklyOrderEntity> findWeeklyListReport();
}
