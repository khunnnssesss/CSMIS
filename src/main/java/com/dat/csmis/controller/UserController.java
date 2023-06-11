package com.dat.csmis.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dat.csmis.entity.AnnounceEntity;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.FeedbackEntity;

import com.dat.csmis.entity.RegisterInfo;
import com.dat.csmis.entity.UserViewEntity;
import com.dat.csmis.repository.AnnounceRepository;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.repository.UserViewRepository;
import com.dat.csmis.security.SecurityConfig;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.FeedbackService;
import com.dat.csmis.service.ImportService;
import com.dat.csmis.service.MenuService;
import com.dat.csmis.service.RegisterService;
import com.dat.csmis.service.RestaurantService;

@Controller("/user")
public class UserController {
	@Autowired
	private EmployeeRepository emp;
	@Autowired 
	ImportService service;
	@Autowired
	RestaurantService serviceR;
	@Autowired
	SecurityConfig config;
	@Autowired
	EmployeeService serviceE;
	@Autowired
	MenuService serviceM;
	@Autowired
	RegisterService registerService;
	@Autowired
	UserViewRepository uvRepo;
	@Autowired 
	FeedbackService feedbackService;
	@Autowired
	RegisterService registerSer;
	@Autowired
	AnnounceRepository announceRepo;
	

	@GetMapping("/user/dashboard")
  public String user(Model m) {
      
    LocalDateTime dateTime= LocalDateTime.now();
    System.out.println("CURRENT DATE AND TIME "+dateTime);
    List<AnnounceEntity> list=announceRepo.findByDate(dateTime);
    List<AnnounceEntity> annlist=new ArrayList<AnnounceEntity>(list);
    Collections.reverse(annlist);
    List<String> subject= new ArrayList<String>();
    List<String> content= new ArrayList<String>();
    List<String> img= new ArrayList<String>();
    List<String> dnt= new ArrayList<String>();
    List<String> dateNow= new ArrayList<String>();
    
    String concat="/images/";
    for(AnnounceEntity a :annlist) {
      subject.add(a.getSubject());
      content.add(a.getContant());
      img.add(concat+a.getImage());
      dnt.add(String.valueOf(a.getDateTime()));
      dateNow.add(a.getDateNow());
      
    }
    HashSet<String> set = new HashSet<>();
    set.add("#EBB9D2");
    set.add("#FE9968");
    set.add("#7FE0EB");

	set.add("#6CE5B1");
    set.add("#a9ebba");
    set.add("#9597de");
    set.add("#f07ddd");
    set.add("#dcf086");
    
    var subArray=subject;
    var conArray=content;
    var imgArray=img;
    var dateArray=dateNow;
    var color=set;
    System.out.println("SUBJECT===..>> "+subArray);
    m.addAttribute("subject", subArray);  
    m.addAttribute("content", conArray);  
    m.addAttribute("img", imgArray);  
    //m.addAttribute("dnt", dnt);  
    m.addAttribute("dateNow", dateArray);  
    m.addAttribute("color", color);

    return "userMenu";
  }
  
	
	@GetMapping("/user/register")
	public String registerPage(Principal pr,Model model) {
		
		EmployeeEntity a = emp.getByEmail(pr.getName());
		model.addAttribute("doorlog", a.getDoorLog() );
		model.addAttribute("empID", a.getStaffId());

		EmployeeEntity emp = serviceE.getOneEmp(pr.getName());
		model.addAttribute("userId", emp.getStaffId());
		model.addAttribute("userDoorlog", emp.getDoorLog());
		boolean registeredUser = registerSer.checkUserStaff(emp.getStaffId());
		model.addAttribute("registered", registeredUser);
		model.addAttribute("userType", emp.getRole());
	
		System.out.println("user info ::: "+pr.getName()+" L:: id is "+a.getId());
		
		
		 RegisterInfo info = registerService.getdateList("26-99917");
  
	     List<UserViewEntity> all = uvRepo.findAll();
	     
	     List<UserViewEntity> RElists = uvRepo.findREdate(a.getDoorLog());
	     List<UserViewEntity> RNElists = uvRepo.findRNEdate(a.getDoorLog());
	     List<UserViewEntity> UElists = uvRepo.findUEdate(a.getDoorLog());
	       
	      model.addAttribute("doorLogNo", all);
	      model.addAttribute("RElists",RElists);
	      model.addAttribute("RNElists",RNElists);
	      model.addAttribute("UElists",UElists);
	      
	      System.out.println("DoorLogNo :: " + all);
	      System.out.println("RE Dates :: " + RElists + "RNE Dates :: " + RNElists + "UE Dates :: " + UElists);
	    
	    
	    return "userRegister";
	}
	@PostMapping("/user/profile")
	public String doProfile(@RequestParam("propic")MultipartFile part,
							@RequestParam("sid")String staffid,
							@RequestParam("name")String name,
							@RequestParam("opsw")String opsw,
							@RequestParam("npsw")String npsw,
							RedirectAttributes m,
							HttpServletRequest req) {
		
		BCryptPasswordEncoder encoder=config.encoder();		
		String fileName;
		Optional<EmployeeEntity> ee= serviceE.selectby(staffid);
		System.out.println("Part is "+part);
	
		if(ee.isPresent() && BCrypt.checkpw(opsw, ee.get().getPassword())) {
			EmployeeEntity entity=ee.get();
			System.out.println("in if case");
			if(!part.isEmpty()) {
				
			
			MultipartFile multipartFile = part;
			fileName = multipartFile.getOriginalFilename();
			System.out.println("File Name 1 "+fileName);
			String system=System.getProperty("user.dir").replace("\\" , "/");
			String uploadPath=system+"/src/main/resources/static/images/"+fileName;
			
			try {
				FileOutputStream fos=new FileOutputStream(uploadPath);
				InputStream is=multipartFile.getInputStream();
				byte[] data=new byte[is.available()];
				is.read(data);
			
					fos.write(data);
				fos.close();
			}catch(Exception e) {
				System.out.println("file input error"+e.getMessage());
			}
			
			
			}else {
				fileName=entity.getPhoto();
				System.out.println("File Name 2 "+ fileName);
				
			}
			System.out.println(fileName);
			System.out.println(staffid);
			System.out.println(name);
			System.out.println(opsw);
			System.out.println(npsw);
			String password=null;
			if(npsw==null || npsw.equals("")) {
				password=opsw;
			}else {
				password=npsw;
			}
			entity.setStaffId(staffid);
			entity.setName(name);
			entity.setPassword(encoder.encode(password));
			entity.setPhoto(fileName);
			serviceE.save(entity);
			m.addFlashAttribute("userProfileMessage","success");
			
		}else {
			m.addFlashAttribute("userProfileMessage", "fail");
			
			System.out.println("in else case !!");
		}		

		String referer = req.getHeader("Referer");

		if(referer != null){
			return "redirect:"+referer;
		}
		
		return "redirect:/user/dashboard";
	}
	@GetMapping("/user/feedbackPage")
	public String displayFeedbackPage(Model model) {
		model.addAttribute("feedback",new FeedbackEntity());
		return "userFeedBack";
	}
	@PostMapping("/user/submitFeedback")
	public String displayFeedbackSubmit(@RequestParam("feedback") String feedback,Model m) {
		System.out.print("feedback-------"+feedback);

		Date currentDate = new Date();
SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
String formattedDate = inputFormat.format(currentDate);

		FeedbackEntity feed=new FeedbackEntity();
		feed.setCreated_at(formattedDate);
		feed.setFeedback(feedback);
		feedbackService.save(feed);
			
		m.addAttribute("message","true");
		return "userFeedBack";	
	}
	@GetMapping("/user/AboutUs")
  public String showAboutUs(Model model) {
    
    return "userAboutUs";

}
	// @GetMapping("/user/userAnn")
	// public String userAnnounce(Model m) {
	// 	LocalDateTime dateTime= LocalDateTime.now();
	// 	System.out.println("CURRENT DATE AND TIME "+dateTime);
	// 	List<AnnounceEntity> list=announceRepo.findByDate(dateTime);
	// 	List<AnnounceEntity> annlist=new ArrayList<AnnounceEntity>(list);
	// 	Collections.reverse(annlist);
	// 	m.addAttribute("userAnn", annlist);	
	// 	return "userAnnounce";
	// }


	@GetMapping("/user/historyRegister")
  public String userHistory(Principal userobj,Model model){

	EmployeeEntity emp = serviceE.getOneEmp(userobj.getName());
		model.addAttribute("userId", emp.getStaffId());
		model.addAttribute("userDoorlog", emp.getDoorLog());
		boolean registeredUser = registerSer.checkUserStaff(emp.getStaffId());
		model.addAttribute("registered", registeredUser);

	return "userRegisterHistory";
  }

}
