package com.dat.csmis.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
public class EmployeeEntity implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true , nullable = false)
	private String staffId;
	@Column(unique = true , nullable = false)
	private String doorLog;
	@Column(nullable = false)
	private String name;
	@Column(unique = true , nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String team;
	@Column(nullable = false)
	private String role;
	@Column(nullable = false)
	private String dept;
	@Column(nullable = false)
	private String division;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private String photo;
	@Column(nullable = true, length = 45)
	private String resetPasswordToken;
	
	
	
	
	
	
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public EmployeeEntity(long id, String staffId, String doorLog, String name, String email, String password,
			String team, String role, String dept, String division, String status, String photo) {
		this.id = id;
		this.staffId = staffId;
		this.doorLog = doorLog;
		this.name = name;
		this.email = email;
		this.password = password;
		this.team = team;
		this.role = role;
		this.dept = dept;
		this.division = division;
		this.status = status;
		this.photo = photo;
	}
	
	public EmployeeEntity() {
		
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDoorLog() {
		return doorLog;
	}

	public void setDoorLog(String doorLog) {
		this.doorLog = doorLog;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	

	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", staffId=" + staffId + ", doorLog=" + doorLog + ", name=" + name
				+ ", email=" + email + ", password=" + password + ", team=" + team + ", role=" + role + ", dept=" + dept
				+ ", division=" + division + ", status=" + status + ", photo=" + photo + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth=new ArrayList<GrantedAuthority>();
		if(this.getRole()!=null && !this.getRole().isEmpty()) {
			auth.add(new SimpleGrantedAuthority("ROLE_"+this.getRole()));
		}
		return auth;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
//	@OneToMany(mappedBy = "employee")
//	private List<RegisterEntity> registered;
}
