package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dat.csmis.entity.DoorLogViewEntity;
import com.dat.csmis.entity.EmpDoorlog;

@Repository
public interface DoorlogViewRepository extends JpaRepository<DoorLogViewEntity, String> 
{
	
   @Query("SELECT d FROM DoorLogViewEntity d WHERE d.date = ?1 AND d.eat = 'yes' AND d.status = 'register'")
   List<DoorLogViewEntity> findRE(String date);
  
   @Query("SELECT d FROM DoorLogViewEntity d WHERE d.date = ?1 AND d.eat = 'no' AND d.status = 'register'")
   List<DoorLogViewEntity> findRNE(String date);
 
   @Query("SELECT d FROM DoorLogViewEntity d WHERE d.date = ?1 AND d.eat = 'yes' AND d.status = 'unregister'")
   List<DoorLogViewEntity> findUE(String date);
   
  
   
   @Query("SELECT d.date FROM DoorLogViewEntity d WHERE d.doorLogNo = ?1 AND d.eat = 'no' AND d.status = 'register'")
   List<String> findRNEdate(String doorLogNo);
   
   @Query("SELECT d.date FROM DoorLogViewEntity d WHERE d.doorLogNo = ?1 AND d.eat = 'yes' AND d.status = 'register'")
   List<String> findREdate(String doorLogNo);
   
   @Query("SELECT d.date FROM DoorLogViewEntity d WHERE d.doorLogNo = ?1 AND d.eat = 'yes' AND d.status = 'unregister'")
   List<String> findUEdate(String doorLogNo); 
   

    // SEARCH FOR WEEKLY REGISTER LISTS
    @Query("SELECT new com.dat.csmis.entity.EmpDoorlog(d.date,d.eat,d.status) FROM DoorLogViewEntity d where d.doorLogNo = ?1 and d.date between ?2 and ?3")
    List<EmpDoorlog> getDoorlogWeeklyLists(String doorlogno, String startdatem,String enddate);
    
   // SEARCH FOR MONTHLY REGISTER LISTS AND MONTHLY COST
   @Query("SELECT new com.dat.csmis.entity.EmpDoorlog(d.date,d.eat,d.status) FROM DoorLogViewEntity d where d.doorLogNo = ?1 and d.date like ?2%")
   List<EmpDoorlog> getDoorlogMonthlyLists(String doorlogno, String monthday);

   
  
   
  
}
