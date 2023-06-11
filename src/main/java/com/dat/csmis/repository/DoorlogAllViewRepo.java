package com.dat.csmis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.DoorLogAllViewEntity;
import com.dat.csmis.entity.DoorLogViewEntity;
import com.dat.csmis.entity.YourEntityKey;

public interface DoorlogAllViewRepo extends JpaRepository<DoorLogAllViewEntity, YourEntityKey> {
	@Query("SELECT DISTINCT d  FROM DoorLogAllViewEntity d")
   	Page<DoorLogAllViewEntity> findAll(Pageable pageable);
	
	@Query("SELECT DISTINCT d  FROM DoorLogAllViewEntity d ")
	List<DoorLogAllViewEntity> getDoorLogAllView();
   
   @Query("SELECT  d  FROM DoorLogAllViewEntity d WHERE (d.key.date BETWEEN ?1 AND ?2) AND d.status=?3")
   Page<DoorLogAllViewEntity> findAllDoor(Pageable pageable,String start,String end,String status);
   
   @Query("SELECT  d  FROM DoorLogAllViewEntity d WHERE (d.key.date BETWEEN ?1 AND ?2)")
   Page<DoorLogAllViewEntity> findAllDoor(Pageable pageable,String start,String end);
   //search with status
   @Query("SELECT  d  FROM DoorLogAllViewEntity d WHERE d.status=?1")
   Page<DoorLogAllViewEntity> findAllDoor(Pageable pageable,String status);
   
   @Query("SELECT  d  FROM DoorLogAllViewEntity d WHERE (d.key.date BETWEEN ?1 AND ?2) AND d.status=?3")
   List<DoorLogAllViewEntity> getDoorLogSearch(String start,String end,String status);
   
   @Query("SELECT  d  FROM DoorLogAllViewEntity d WHERE (d.key.date BETWEEN ?1 AND ?2)")
   List<DoorLogAllViewEntity> findAllDoorByStartEndreport(String start,String end);
   
   @Query("SELECT  d  FROM DoorLogAllViewEntity d WHERE d.status=?1")
   List<DoorLogAllViewEntity> findAllDoorByStatusreport(String status);
   
   @Query("SELECT MONTH(d.key.date), " +
           "SUM(CASE WHEN d.eat = 'yes' AND d.status = 'register' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN d.eat = 'no' AND d.status = 'register' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN d.eat = 'yes' AND d.status = 'unregister' THEN 1 ELSE 0 END) " +
           "FROM DoorLogAllViewEntity d " +
           "WHERE YEAR(d.key.date) = YEAR(CURRENT_DATE) " +
           "GROUP BY MONTH(d.key.date)")
    List<Object[]> getCountsByMonth();
}
