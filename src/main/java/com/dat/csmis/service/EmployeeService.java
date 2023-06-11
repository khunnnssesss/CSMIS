package com.dat.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository repo;
	
	public long count() {
		return repo.count();
	}
	
	public void save(EmployeeEntity entity) {
		repo.save(entity);
	}
	
	public List<EmployeeEntity> NameByRole(){
		List<EmployeeEntity> nameList=repo.getNameByRole();
		return nameList;
		
	}
	
	public Optional<EmployeeEntity> selectOne(long id){
		return repo.findById(id);
	}
	
	public Optional<EmployeeEntity> selectby(String staffId){
		return repo.findByStaffIdAndPsw(staffId);
	}
	public void update(String staffId,String doorLog, String email,
	          String team,String role,
	          String dept,String division,String status,long id) {
		
		repo.update(staffId, doorLog, email,team, role, dept, division, status, id);
	}
	public void updateStatus(long id) {
		repo.updateStatus(id);
	}

	
	public void updateResetPasswordToken(String token, String email) throws EmployeeNotFoundException {
		EmployeeEntity entity=repo.getByEmail(email);
		if(entity != null) {
			entity.setResetPasswordToken(token);
			repo.save(entity);
		}else {
			throw new EmployeeNotFoundException("Employee could not be found on "+email);
		}
	}
	public EmployeeEntity getByReset(String token) {
		return repo.findByResetPasswordToken(token);
	}
	
	public void updatePassword(EmployeeEntity entity, String newPassword) {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		String encodedPsw = encoder.encode(newPassword);
		entity.setPassword(encodedPsw);
		entity.setResetPasswordToken(null);
		
		repo.save(entity);
	}
	
	public void updateA(String status, long id) {
		repo.updateA(status, id);
	}
	public void updateB(String role, long id) {
		repo.updateB(role, id);
	}
	public boolean getByMail(String mail){
		return repo.existsByEmail(mail);
		}
	
		public EmployeeEntity getOneEmp(String mail){
			return repo.getByEmail(mail);
		}

		public EmployeeEntity getOnestaffid(String staffid){
			return repo.getByStaffId(staffid);
		}

		  public List<EmployeeEntity> getAllEmployees() {
				List<EmployeeEntity> empList = getRepo().findAllbutOne();
				
				System.out.println("Retrieved holidays from database: " + empList);
				return empList;
			}

			public EmployeeRepository getRepo(){

				return  repo;
			}

			public void setRepo(EmployeeRepository repo){
			this.repo = repo;
}
}