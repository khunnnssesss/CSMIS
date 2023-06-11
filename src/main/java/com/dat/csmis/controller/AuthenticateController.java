package com.dat.csmis.controller;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dat.csmis.Utility;
import com.dat.csmis.entity.AnnounceEntity;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.LunchViewEntity;
import com.dat.csmis.entity.OrderListEntity;
import com.dat.csmis.entity.RestaurantEntity;
import com.dat.csmis.model.Announce;
import com.dat.csmis.model.ChartAvoidMeat;
import com.dat.csmis.model.MonthCountBean;
import com.dat.csmis.repository.AnnounceRepository;
import com.dat.csmis.repository.AuthenticateRepository;
import com.dat.csmis.service.AvoidMeatService;
import com.dat.csmis.service.DoorLogAllViewSer;
import com.dat.csmis.service.DoorLogService;
import com.dat.csmis.service.EmployeeNotFoundException;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.FeedbackService;
import com.dat.csmis.service.OrderListService;
import com.dat.csmis.service.RegisterService;
import com.dat.csmis.service.lunchViewService;


@Controller
public class AuthenticateController {

	@Autowired
	private AuthenticateRepository repo;
	@Autowired
	private RegisterService registerService;
	@Autowired FeedbackService feedbackService;
	@Autowired DoorLogAllViewSer doorlogAllViewSer;
	@Autowired AvoidMeatService avoidMeatService;
	@Autowired DoorLogService doorlogService;
	@Autowired EmployeeService employeeService;
	@Autowired OrderListService orderListService;
	@Autowired private JavaMailSender mailSender;
	@Autowired private AnnounceRepository annRepo;
	@Autowired private RegisterService regSer;
	@Autowired private lunchViewService lunchViewSer;


	@GetMapping("/admin/dashboard")
	public ModelAndView admin(Principal userobj,Model model,HttpServletRequest req) throws IllegalAccessException, InvocationTargetException {

		
		int registerCount=registerService.getRowCount();
		int mailNotiCount=registerService.getMailNoti();
		int feedbackCount=feedbackService.getFeedbackList();
		int doorLogCount=doorlogService.doorLogCountByCurrentdate();

		EmployeeEntity emp = employeeService.getOneEmp(userobj.getName());
		model.addAttribute("userId", emp.getStaffId());
		model.addAttribute("userDoorlog", emp.getDoorLog());
		boolean registeredUser = registerService.checkUserStaff(emp.getStaffId());
		model.addAttribute("registered", registeredUser);
		model.addAttribute("registerCount", registerCount);
		model.addAttribute("mailNotiCount", mailNotiCount);
		model.addAttribute("feedbackCount", feedbackCount);
		model.addAttribute("doorLogCount", doorLogCount);
		List<Object[]> regitserList=doorlogAllViewSer.getCountRegisterByMonth();
		List<Object[]> avoidList=avoidMeatService.avoidMeatCount();
		List<MonthCountBean> monthCountList=new ArrayList<>();
		List<ChartAvoidMeat> avoidCountList=new ArrayList<>();
		for(Object[] result:regitserList) {
			 Integer month = (Integer) result[0];
			    Long registerEatCount = (Long) result[1];
			    Long registerNotEatCount = (Long) result[2];
			   Long unregisterEatCount = (Long) result[3];
			MonthCountBean monthCount=new MonthCountBean();
			monthCount.setMonth(month);
			monthCount.setRegisterEat(registerEatCount);
			monthCount.setRegisternoEat(registerNotEatCount);
			monthCount.setUnregisterEat(unregisterEatCount);
			
			monthCountList.add(monthCount);
		}
		for(Object[] avoid:avoidList) {
			String avoidMeat=(String)avoid[0];
			Long count=(Long)avoid[1];
			ChartAvoidMeat avoidCount=new ChartAvoidMeat();
			avoidCount.setAvoidMeat(avoidMeat);
			avoidCount.setAvoidCount(count);
			avoidCountList.add(avoidCount);
			
		}
		
		var dateTime= LocalDateTime.now();
	var list=  annRepo.findByDate(dateTime);
		var annlist=new ArrayList<AnnounceEntity>(list);
		Collections.reverse(annlist);
		req.getSession().removeAttribute("annList");
		req.getSession().setAttribute("annList", annlist);

		
		 model.addAttribute("monthCountList", monthCountList);
		 model.addAttribute("avoidCountList", avoidCountList);
		 
		 //cost
OrderListEntity orderCost=orderListService.getCurrentWeekOrderList();
if (orderCost == null) {
orderCost = new OrderListEntity();
}
Integer totalPeople=orderCost.getTotalPeople();
if (totalPeople == null) {
totalPeople = Integer.valueOf(0);
}
double datCost = 0.0;
double summaryCost = 0.0;
double empCost = 0.0;
//Employee cost
int re=0;
int ue=0;
int rne=0;
int empCount=0;
List<LunchViewEntity> lunchCost=lunchViewSer.getCurrentWeekDoorlogAccess();
if(lunchCost!=null) {
for(LunchViewEntity lunchEmp:lunchCost) {
re+=lunchEmp.getRe();
ue+=lunchEmp.getUe();
rne+=lunchEmp.getRne();
empCount=re+ue+rne;
}
}
RestaurantEntity restaurantEntity = orderCost.getRestaurantEntity();
if (restaurantEntity != null) {
double datPrice = restaurantEntity.getPrice().getDatPrice();
double summaryPrice = restaurantEntity.getTotalPrice();
double empPrice = restaurantEntity.getPrice().getEmpPrice();
datCost = totalPeople * datPrice;
summaryCost = totalPeople * summaryPrice;
empCost = empCount * empPrice;
}
model.addAttribute("datCost", datCost);
model.addAttribute("summaryCost", summaryCost);
model.addAttribute("empCost", empCost);
//cost


		return new ModelAndView( "adminDashboard","annbean", new Announce());
	}


	@GetMapping("/login")
	public String showLogin() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		long a=employeeService.count();
		if(a==0) {
			EmployeeEntity entity = new EmployeeEntity();
			entity.setStaffId("000001");
			entity.setDoorLog("");
			entity.setEmail("admin@gmail.com");
			entity.setName("admin");
			entity.setPassword(encoder.encode("11111"));
			entity.setTeam("");
			entity.setRole("ADMIN");
			entity.setDept("");
			entity.setDivision("");
			entity.setStatus("Active");
			entity.setPhoto("avatar.png");
			entity.setResetPasswordToken("");
			employeeService.save(entity);
			System.out.println("In Login "+entity.getEmail());
		}else {
			Optional<EmployeeEntity> op=employeeService.selectOne(1);
			EmployeeEntity e1=op.get();
			e1.setEmail("admin@gmail.com");
			e1.setRole("ADMIN");
			e1.setStaffId("000001");
			e1.setDoorLog("");
			e1.setTeam("");
			e1.setRole("ADMIN");
			e1.setDept("");
			e1.setDivision("");
			e1.setStatus("Active");
			employeeService.save(e1);
			System.out.println("In Login 2 "+e1.getEmail());
		}
		return "login";
	}
	@GetMapping("/")
	public String validateRole(Authentication auth, HttpServletRequest req, Model m) {
		String role=null;
		
		GrantedAuthority authority = auth.getAuthorities().stream()
										 .findFirst().orElse(null);
		if (authority != null) {
            role = authority.getAuthority().substring("ROLE_".length());
        }
		EmployeeEntity entity=repo.findByName(auth.getName());
		boolean register = regSer.checkUserRegi(entity.getStaffId());
		req.getSession().setAttribute("name", entity.getName());
		req.getSession().setAttribute("photo", entity.getPhoto());
		req.getSession().setAttribute("id", entity.getId());
		req.getSession().setAttribute("staffid", entity.getStaffId());
		req.getSession().setAttribute("register", register);
		if ("ADMIN".equals(role) && entity.getStatus().equals("Active")) {
            return "redirect:/admin/dashboard";
        } else if ("USER".equals(role) && entity.getStatus().equals("Active")) {
        	return "redirect:/user/dashboard";
        } 
			else {
				m.addAttribute("status", "You don't have permission to login this website!");
        	return "login";
        }
	}
	

	
}
