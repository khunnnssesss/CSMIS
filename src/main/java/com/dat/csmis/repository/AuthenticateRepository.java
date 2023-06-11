package com.dat.csmis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.EmployeeEntity;



public interface AuthenticateRepository extends JpaRepository<EmployeeEntity, Long> {
	
	@Query("Select s from EmployeeEntity s where s.email=?1")
	public EmployeeEntity findByName(String email);
}
