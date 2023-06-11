package com.dat.csmis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.repository.AuthenticateRepository;

public class UserDetail implements UserDetailsService{
	
	@Autowired
	AuthenticateRepository repo;

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		EmployeeEntity emp=repo.findByName(username);
		if (emp == null) {
			throw new UsernameNotFoundException("User not found");
		}
		System.out.println("password is  "+emp.getPassword());
		System.out.println("employee"+emp.getName());
		return emp;
	}
}
