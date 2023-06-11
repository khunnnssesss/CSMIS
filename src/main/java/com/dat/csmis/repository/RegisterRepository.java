package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.RegisterInfo;

public interface   RegisterRepository extends JpaRepository<RegisterInfo,String>{

	boolean existsByEmpId(String sid);

	
	  @Query("SELECT a.avoid,COUNT(a),d.date from RegisterInfo  r JOIN r.dateList d JOIN r.avoidList a WHERE d.date BETWEEN ?1 AND ?2 GROUP BY a.avoid,d.date")
	  List<Object[]> findEmployeeCountByAvoid(String start,String end);
	  
	  @Query("SELECT r.empId, d.date FROM RegisterInfo r JOIN r.dateList d")
	  List<Object[]> findDoorlogData();
	  
	  @Query("SELECT r FROM RegisterInfo r JOIN r.dateList d WHERE r.empId =?1")
	  RegisterInfo finddateList(String employeeId);
	  
	  @Query("SELECT r.empId FROM RegisterInfo r WHERE r.emailNoti = 1")
	  List<String> findregisterMailEmployees();
	  
	  @Query("SELECT name FROM EmployeeEntity e WHERE e.staffId = empId")
	  RegisterInfo findAll(String staffId);

	  @Query("SELECT u FROM UserViewEntity u JOIN RegisterInfo r ON u.doorLogNo = r.doorlog")
	   List<Object[]> findRElists();
	   
	   @Query("SELECT COUNT(r) FROM RegisterInfo r  WHERE r.emailNoti = true")
	    int countByMailNotiEqualsOne();
	   
}
