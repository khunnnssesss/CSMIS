package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dat.csmis.entity.UserViewEntity;


 
	@Repository
	public interface UserViewRepository extends JpaRepository<UserViewEntity, String> {
		
		 @Query("SELECT u FROM UserViewEntity u WHERE u.doorLogNo= ?1 AND u.status = 'RE'")
		   List<UserViewEntity> findREdate(String doorLogNo);
		   
		   @Query("SELECT u FROM UserViewEntity u WHERE u.doorLogNo= ?1 AND u.status = 'RNE'")
		   List<UserViewEntity> findRNEdate(String doorLogNo);
		   
		   @Query("SELECT u FROM UserViewEntity u WHERE u.doorLogNo= ?1 AND u.status = 'UE'")
		   List<UserViewEntity> findUEdate(String doorLogNo);

			
			//  @Query("SELECT u FROM UserViewEntity u JOIN RegisterInfo r ON u.staffId = r.empId")
			//   List<UserViewEntity> findAll(String staffId);

			@Query("SELECT u FROM UserViewEntity u WHERE u.staffId= ?1")
			 List<UserViewEntity> findUserLuncInfo(String staffId);
			 
}
