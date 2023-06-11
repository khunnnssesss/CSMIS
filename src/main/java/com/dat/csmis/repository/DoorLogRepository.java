package com.dat.csmis.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.DoorLogEntity;

public interface DoorLogRepository extends JpaRepository<DoorLogEntity,Long> {
	  @Query("SELECT COUNT(d) FROM DoorLogEntity d WHERE d.key.date = CURRENT_DATE")
	    int countEntriesForCurrentDate();
	  
	  @Query("select distinct d.key.date from DoorLogEntity d")
	  @Modifying
	  @Transactional
	  List<String> selectDate();
}
