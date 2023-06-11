package com.dat.csmis.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dat.csmis.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
	EmployeeEntity getByEmail(String emp);

	EmployeeEntity getByStaffId(String staffid);


	@Query("select e from EmployeeEntity e where e.staffId=?1")
	  Optional<EmployeeEntity> findByStaffId(String staffId);
	
	  @Query("select e from EmployeeEntity e where e.role='ADMIN' and e.dept='Finance Dept' and e.status='Active' ")
	List<EmployeeEntity> getNameByRole();

	EmployeeEntity getByName(String emp);
	  
	  @Modifying
	  @Transactional
	  @Query("update EmployeeEntity e set e.staffId= ?1, e.doorLog= ?2, e.email= ?3,"
	      + "e.team= ?4, e.role= ?5, e.dept= ?6, e.division= ?7, e.status= ?8 "
	      + "where e.id= ?9")
	    void update(String staffId,String doorLog, String email,
	          String team,String role,
	          String dept,String division,String status, long id);
	  @Query("select e from EmployeeEntity e where e.staffId=?1")
	  Optional<EmployeeEntity> findByStaffIdAndPsw(String staffId);
	  
	  @Query("update EmployeeEntity e set e.status='InActive' where e.id= ?1")
	  void updateStatus(long id);
	  
	  @Query("Select e from EmployeeEntity e inner join RegisterInfo s on e.staffId = s.empId where e.status = 'Active'")
	  @Modifying
	  @Transactional
	  List<EmployeeEntity> findFromTwoTables();

	  boolean existsByEmail(String mail);

	  void deleteEmployeeByStaffId(String staffId);
	  @Query("select e from EmployeeEntity e where e.resetPasswordToken = ?1")
	  public EmployeeEntity findByResetPasswordToken(String token);
	  
	  @Query("select e from EmployeeEntity e where e.id != 1")
	  public List<EmployeeEntity> findAllbutOne();
	  @Query("update EmployeeEntity e set e.status= ?1 where e.id= ?2")
	  @Modifying
	  @Transactional
	  public void updateA(String status, long id);
	  @Query("update EmployeeEntity e set e.role= ?1 where e.id= ?2")
	  @Modifying
	  @Transactional
	  public void updateB(String role, long id);
}
