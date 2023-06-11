package com.dat.csmis.service;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.RegisterInfo;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.repository.RegisterRepository;

@Service
public class RegisterService {
    
    @Autowired RegisterRepository registerRepo;
    @Autowired EmployeeRepository repo;

    public RegisterInfo getdateList(String employeeId){
        RegisterInfo info = registerRepo.finddateList(employeeId);
        
        return info;
      }
      
      public RegisterInfo getAll(String employeeId) {
        RegisterInfo rf = registerRepo.findAll(employeeId);
        
        return rf;
      }

      public boolean checkUserRegi(String userid){

        return registerRepo.existsById(userid);
      }
      public RegisterInfo getAuser(String empid){
        return registerRepo.findById(empid).orElse(new RegisterInfo());
      }
    
      public boolean checkUserStaff(String staffid){
    
        return registerRepo.existsByEmpId(staffid);
      }
    
    public void saveData(RegisterInfo registerInfo){
        System.out.print(registerRepo.save(registerInfo));
    }
    public List<EmployeeEntity> findJoin(){
    	return repo.findFromTwoTables();
    }
    public List<Object[]> getAvoidMeatByist(){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Calendar calendar = Calendar.getInstance();
		//System.out.println("Now: " + calendar.getTime());
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		//System.out.println("Next Week: " + calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		//System.out.println("Next Week Monday: " + calendar.getTime());
		String startDate = dateFormat.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		//System.out.println("Next Week FRIDAY: " + calendar.getTime());
		String endDate = dateFormat.format(calendar.getTime());
    	List<Object[]> list=registerRepo.findEmployeeCountByAvoid(startDate,endDate);
    	return list;
    }
    public int getRowCount() {
        return (int) registerRepo.count();
    }
    public int getMailNoti() {
    	return registerRepo.countByMailNotiEqualsOne();
    }

    public List<String> getallmailregister(){

      return registerRepo.findregisterMailEmployees();
    }
}
