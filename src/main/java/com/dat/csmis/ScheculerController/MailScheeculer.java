package com.dat.csmis.ScheculerController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.model.WeeklyLists;
import com.dat.csmis.service.DailyEatViewService;
import com.dat.csmis.service.DateListEntityService;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.MailSer;
import com.dat.csmis.service.RegisterService;

@Service
public class MailScheeculer {
    
    @Autowired private RegisterService regSer;
    @Autowired private EmployeeService empser;
    @Autowired private DateListEntityService dateListEntityService;
    @Autowired private MailSer mailSer;
    @Autowired private DailyEatViewService dailyEatViewService;
    
    
// Send Weekly Registered days email

@Scheduled(cron = "2 19 * * 5")
public List<String> sendRegisterDayMailAll(){
	var dateformat = new SimpleDateFormat("yyyy-MM-dd");
	var dateformatday = new SimpleDateFormat("EEEE");
	Calendar calendar = Calendar.getInstance();
calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
calendar.add(Calendar.DATE, 7);
String nextWeekStart = dateformat.format(calendar.getTime());
calendar.add(Calendar.DATE, 4);
String nextWeekEnd = dateformat.format(calendar.getTime());

// Get Email On Employees ID
var listdate = regSer.getallmailregister();

for (String empId : listdate) {
	var emp = empser.getOnestaffid(empId);
	var listdats = new ArrayList<WeeklyLists>();

	// Get Email On Employees Registered Dates for Next Week For Each
	var empdate = dateListEntityService.getnext(empId, "2023-05-15", "2023-05-19");

	
	for (String dates : empdate) {
		try {
			var datelist = dateformat.parse(dates);
			String userdate = dateformatday.format(datelist);
			var sendDate = new WeeklyLists(dates, userdate);

			listdats.add(sendDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	mailSer.sendWeeklyEmail(listdats, emp.getEmail());
}

return listdate;

}


// Send Monthly Data to all Employee
@Scheduled(cron ="0 0 2 L * ?")
public String sendAllEmployeeMonthly(){

    var cal = Calendar.getInstance();
    var monthformat = new SimpleDateFormat("yyyy-MM");
    String curmonthdate = monthformat.format(cal.getTime());

  try {
    
    var emplists = empser.getAllEmployees();

    for (EmployeeEntity emp : emplists) {
        var datelists = dailyEatViewService.getMonthlyData(emp.getStaffId(), "2023-05");
        
        System.out.println("employee List:: ");
        System.out.println(datelists);
        mailSer.sendMonthlyEmail(datelists, emp.getEmail());
    }

    return "email Send success !!!";

  } catch (Exception e) {
    // TODO: handle exception
    e.printStackTrace();
    System.out.println("eeror ::: Error!!!");

    return "email send Failed !!!";
  }

}


}





