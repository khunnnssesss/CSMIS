package com.dat.csmis.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dat.csmis.entity.AnnounceEntity;
import com.dat.csmis.entity.DailyVoucherEntity;
import com.dat.csmis.entity.DoorLogAllViewEntity;
import com.dat.csmis.entity.DoorLogEntity;
import com.dat.csmis.entity.DoorLogViewEntity;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.FeedbackEntity;
import com.dat.csmis.entity.HolidaysEntity;
import com.dat.csmis.entity.LunchViewEntity;
import com.dat.csmis.entity.MeatList;
import com.dat.csmis.entity.OrderListEntity;
import com.dat.csmis.entity.PriceEntity;
import com.dat.csmis.entity.RestaurantEntity;
import com.dat.csmis.entity.WeeklyOrderEntity;
import com.dat.csmis.entity.YourEntityKey;
import com.dat.csmis.model.Announce;
import com.dat.csmis.model.AvoidMeatBean;
import com.dat.csmis.model.CustomeCost;
import com.dat.csmis.model.DoorlogReportBean;
import com.dat.csmis.model.JasperOrderBean;
import com.dat.csmis.model.OrderList;
import com.dat.csmis.model.PaymentCashierBean;
import com.dat.csmis.model.ReportCost;
import com.dat.csmis.model.RestaurantModel;
import com.dat.csmis.model.WeeklyOrderBean;
import com.dat.csmis.repository.AnnounceRepository;
import com.dat.csmis.repository.AvoidMeatRepository;
import com.dat.csmis.repository.DoorlogViewRepository;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.repository.HolidaysRepository;
import com.dat.csmis.repository.OrderListRepository;
import com.dat.csmis.repository.RestaurantRepository;
import com.dat.csmis.repository.lunchViewRepository;
import com.dat.csmis.security.SecurityConfig;
import com.dat.csmis.service.AvoidMS;
import com.dat.csmis.service.AvoidMeatService;
import com.dat.csmis.service.DateListEntityService;
import com.dat.csmis.service.DoorLogAllViewSer;
import com.dat.csmis.service.DoorLogService;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.FeedbackService;
import com.dat.csmis.service.HolidayService;
import com.dat.csmis.service.ImportService;
import com.dat.csmis.service.OrderListService;
import com.dat.csmis.service.RegisterService;
import com.dat.csmis.service.RestaurantService;
import com.dat.csmis.service.WeeklyOrderService;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

@Controller
public class AdminController {
	@Autowired DateListEntityService dateListService;
	@Autowired ImportService service;
	@Autowired RestaurantService serviceR;
	@Autowired SecurityConfig config;
	@Autowired EmployeeService serviceE;
	@Autowired EmployeeService employeeService;
	@Autowired WeeklyOrderService weeklyOrderService;
	@Autowired OrderListService orderListService;
	@Autowired RegisterService registerSer;
	@Autowired RegisterService serviceRS;
	@Autowired DoorlogViewRepository dlRepo;
	@Autowired lunchViewRepository lvRepo;
	@Autowired 
    FeedbackService feedbackService;
	@Autowired RestaurantRepository restaurantRepository;
	@Autowired
	DoorLogAllViewSer doorlogallViewSer;
	@Autowired 
	OrderListRepository orderListRepo;
	@Autowired DoorLogService doorlogService;
	@Autowired AvoidMeatRepository avoidMeatRepo;
	@Autowired AvoidMeatService avoidMeatService;
	@Autowired HolidayService hdService;
	@Autowired HolidaysRepository hdRepo;
	@Autowired EmployeeRepository employeeRepo;
	@Autowired AvoidMS avoidms;
	@Autowired AnnounceRepository announceRepo;

	@GetMapping("/admin/import")
	public String importPage(Principal userobj,Model model) {

		EmployeeEntity emp = employeeService.getOneEmp(userobj.getName());
		model.addAttribute("userId", emp.getStaffId());
		model.addAttribute("userDoorlog", emp.getDoorLog());
		boolean registeredUser = registerSer.checkUserStaff(emp.getStaffId());
		model.addAttribute("registered", registeredUser);
		return "import";
	}
	
	@PostMapping("/admin/uploadUser")
	public String userFile(@RequestParam("userFile")MultipartFile file, Model m) {
		boolean condition=service.importEmployee(file);
		if(condition) {
			m.addAttribute("color", "alert alert-success alert-dismissible fade show");
			m.addAttribute("messageEmp", "Successfully Imported..!");
			return "import";
		}else {
			m.addAttribute("color", "alert alert-danger alert-dismissible fade show");
			m.addAttribute("messageEmp", "Import Failed..!");
			return "import";

		}
	}
	@GetMapping("/admin/register")
	public String displayAdminRegister(Principal userobj,Model model) {
		EmployeeEntity emp = employeeService.getOneEmp(userobj.getName());
		model.addAttribute("userId", emp.getStaffId());
		model.addAttribute("userDoorlog", emp.getDoorLog());
		boolean registeredUser = registerSer.checkUserStaff(emp.getStaffId());
		model.addAttribute("registered", registeredUser);
		model.addAttribute("userType", emp.getRole());
		return "adminRegister";
	}

	@GetMapping("/admin/historyRegister")
	public String showHistory(Principal userobj,Model model){
		EmployeeEntity emp = employeeService.getOneEmp(userobj.getName());
		model.addAttribute("userId", emp.getStaffId());
		model.addAttribute("userDoorlog", emp.getDoorLog());
		boolean registeredUser = registerSer.checkUserStaff(emp.getStaffId());
		model.addAttribute("registered", registeredUser);
		return "historyDataPage";
	}
	
	@PostMapping("/admin/holiday")
	public String dayFile(@RequestParam("dayFile")MultipartFile file, Model m) {
		boolean condition=service.importHoliday(file);
		if(condition) {
			m.addAttribute("color", "alert alert-success alert-dismissible fade show");
			m.addAttribute("messageHoli", "Successfully Imported..!");
			return "import";
		}else {
			m.addAttribute("color", "alert alert-danger alert-dismissible fade show");
			m.addAttribute("messageHoli", "Import Failed..!");
			return "import";

		}
	}
	@PostMapping("/admin/doorlog")
	public String doorFile(@RequestParam("doorLog")MultipartFile file, Model m) {
		boolean condition=service.importDoorlog(file);
		
		if(condition) {
			m.addAttribute("color", "alert alert-success alert-dismissible fade show");
			m.addAttribute("messageDoor", "Successfully Imported..!");
			return "import";
		}else {
			m.addAttribute("color", "alert alert-danger alert-dismissible fade show");
			m.addAttribute("messageDoor", "Import Failed..!");
			return "import";

		}
	}
	@PostMapping("/admin/pdf")
	public String doorPdfFile(@RequestParam("pdf")MultipartFile file, Model m) {
		boolean condition=service.importMenu(file);
		
		if(condition) {
			m.addAttribute("color", "alert alert-success alert-dismissible fade show");
			m.addAttribute("messagePdf", "Successfully Imported..!");
			return "import";
		}else {
			m.addAttribute("color", "alert alert-danger alert-dismissible fade show");
			m.addAttribute("messagePdf", "Import Failed..!");
			return "import";

		}
	}
	@GetMapping("/admin/res")
	public ModelAndView resturant() {
		return new ModelAndView("adminRestaurant","res", new RestaurantModel());
	}



	
	@PostMapping("/admin/doRes")
  public String doRestaurant(@ModelAttribute("res") RestaurantModel rm, @RequestParam("datPrice") Double datprice,
      @RequestParam("empPrice") Double empPrice, RedirectAttributes ra,Model model) {
      model.addAttribute("datPrice", datprice);
      model.addAttribute("empPrice", empPrice);
      //if (rm != null) {
        RestaurantEntity entity = new RestaurantEntity();
        
        List<RestaurantEntity> res=restaurantRepository.findAllByEmailNotNull();
    //
    for(RestaurantEntity rr:res) {
      System.out.println(rr.getEmail());
    if (rr.getEmail().equals(rm.getEmail()) && restaurantRepository.existsByPhone(rm.getPhone())) {
    
          model.addAttribute("errorS", "Email and phone is duplicate");
          return "adminRestaurant";
          } else if (rr.getEmail().equals(rm.getEmail())) {
          model.addAttribute("errorS", "Email is duplicate");
          return "adminRestaurant";
      
          } else if (restaurantRepository.existsByPhone(rm.getPhone())) {
          model.addAttribute("errorS", "Phone is duplicate");
          return "adminRestaurant";
      
          }
      
      
    }
        
        entity.setName(rm.getName());
        entity.setEmail(rm.getEmail());
        entity.setPhone(rm.getPhone());
        entity.setReceiveBy(rm.getReceiveBy());
        entity.setTotalPrice(rm.getCost());
        entity.setAddress(rm.getAddress());
        entity.setStatus("InActive");

        PriceEntity pe = new PriceEntity();
        pe.setDatPrice(datprice);
        pe.setEmpPrice(empPrice);

        entity.setPrice(pe);
        serviceR.save(entity);
        int a = serviceR.count();
        System.out.println("Table count is " + a);
        serviceR.updateA("Active", a);
        serviceR.updateC("InActive", a);
        ra.addAttribute("success", "true");

        return "redirect:/admin/res";
	}

	
	@PostMapping("/admin/updateRes")
  public String updateRestaurant(@ModelAttribute("res") RestaurantEntity rm,
      @RequestParam("datPrice") Double datprice, @RequestParam("empPrice") Double empPrice,
      RedirectAttributes ra) {
    if (rm != null) {
      System.out.println("RESTAURANT ENTITY ===> " + rm.getId());
      Optional<RestaurantEntity> res = serviceR.findById(rm.getId());
      if (res.isPresent()) {
        RestaurantEntity entity = res.get();
        entity.setName(rm.getName());
        entity.setEmail(rm.getEmail());
        entity.setPhone(rm.getPhone());
        entity.setReceiveBy(rm.getReceiveBy());
        entity.setTotalPrice(rm.getTotalPrice());
        entity.setAddress(rm.getAddress());
        entity.setStatus(rm.getStatus());

        PriceEntity pe = new PriceEntity();
        pe.setDatPrice(datprice);
        pe.setEmpPrice(empPrice);

        entity.setPrice(pe);
        System.out.println("the id is ==> " + entity.getId());
        
        serviceR.save(entity, entity.getId());
      }
      
      ra.addFlashAttribute("message", "Restaurant data update successfully!");
      return "redirect:/admin/resList";
    } else {
      ra.addFlashAttribute("message", "Restaurant data update successfully!");
      return "redirect:/admin/resList";
    }

  }
  
	
	@PostMapping("/admin/profile")
	public String doProfile(@RequestParam("propic")MultipartFile part,
							@RequestParam("sid")String staffid,
							@RequestParam("userName")String name,
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
			req.getSession().removeAttribute("name");
			req.getSession().removeAttribute("photo");
			req.getSession().setAttribute("name", name);
			req.getSession().setAttribute("photo", fileName);
			m.addFlashAttribute("profileMessage","success");
		}
		else {
			m.addFlashAttribute("profileMessage", "fail");
		}
		
		String referer = req.getHeader("Referer");
		
		if(referer != null) {
			return "redirect:"+referer;
		}
		
		return "redirect:/admin/dashboard";
	}
	/////
	@GetMapping("/admin/mail")
	public String mailList(Model m) {
		List<EmployeeEntity> list= serviceRS.findJoin();
		if(list.isEmpty()) {
			System.out.println("LIST IS EMPTY!!!!!");
		}
		m.addAttribute("mailList", list);
		return "adminNotiList";
	}
	@GetMapping("/admin/deleteMail/{id}")
	public String deleteMail(@PathVariable("id")long id) {
		System.out.println("Mail id is "+id);
		serviceE.updateStatus(id);
		return "redirect:/admin/mail";
	}


	@GetMapping("/admin/resDel/{id}")
  public String updateRes(@PathVariable("id")int id, Model m) {
    System.out.println("Restaurant id is "+id);
    // RestaurantEntity re=null;
    // PriceEntity pe=null;
    Optional<RestaurantEntity> op= serviceR.findById(id);
    // Optional<PriceEntity> op2=serviceR.findByIdForPrice(id);
    if(op.isPresent()) {
     var re=op.get();
    //   var pe=op2.get();
      m.addAttribute("dat", re.getPrice().getDatPrice());
      m.addAttribute("emp", re.getPrice().getEmpPrice());
      m.addAttribute("res", re);
	  	System.out.println("obj :: "+re);

    }
    return "adminUpdateRestaurant";
  }
  

	@GetMapping("/admin/resList")
	public String resList(Model m) {
		List<RestaurantEntity> list= serviceR.selectAll();
		m.addAttribute("resList", list);
		return "adminRestaurantList";
	}
	

	
	
	@GetMapping("/admin/setStatus/{id}/{status}")
	public String setStatus(@PathVariable("id")int id,
							@PathVariable("status")String status){
		System.out.println("Set Status Method "+id+"=>"+status);
		if(status.equals("Active")) {
			serviceR.updateA("InActive", id);
		}else {
			serviceR.updateA("Active", id);
			serviceR.updateB("InActive", id);
		}
		return "redirect:/admin/resList";
	}
	
	
	@GetMapping("/admin/order")
	public String displayOrderPage(Model model) {

		List<WeeklyOrderBean> dateList = new ArrayList<>();
		
		//Monday
		Date monday=orderListRepo.getNextWeekMonday();
		//System.out.println("Monday++++"+monday);
		//OrderListNextWeekEndDate
		String orderEndDate=orderListService.getNextWeekRegisterOrderByEndDate();
		//System.out.println("End date++++"+orderEndDate);
		

		List<EmployeeEntity> nameList = employeeService.NameByRole();
		RestaurantEntity restData = serviceR.getPriceByRestaurant();
		// System.out.print("Price:::"+restPrice.getName()+"");
		List<Object[]> registerOrder=dateListService.getNextWeekRegister();
		int registerOrderCount=orderListService.getNextWeekRegisterOrderCount();
		Integer orderListId=orderListService.getNextWeekRegisterOrderByID();
		
		
			System.out.println("registerOrderCount"+registerOrderCount);
			System.out.println("registerOrderId"+orderListId);
        if(registerOrderCount>0) {
        	
        	
        	model.addAttribute("yesOrder","true");
        }
        else {
        	
        	model.addAttribute("yesOrder","false");
        }
       
		for (Object[] row : dateListService.getNextWeekRegister()) {
			List<AvoidMeatBean> avoidList = new ArrayList<>();
			for (Object[] avoidMeat : registerSer.getAvoidMeatByist()) {
				if (avoidMeat[2].toString().equals(row[0].toString())) {
					avoidList.add(new AvoidMeatBean(avoidMeat[2].toString(), avoidMeat[0].toString(),
							avoidMeat[1].toString()));

				}

			}
			WeeklyOrderBean wob = new WeeklyOrderBean();
			wob.setAvoidList(avoidList);
			wob.setDate(row[0].toString());
			wob.setNoOfPeople(Integer.parseInt(row[1].toString()));
			dateList.add(wob);
		}

		model.addAttribute("name", nameList);
		dateList.sort(Comparator.comparing(o ->o.getDate()));
	
		model.addAttribute("datecount", dateList);
		model.addAttribute("restData", restData);
		model.addAttribute("orderListId", orderListId);
       model.addAttribute("orderEndDate", orderEndDate);
       Calendar calendar = Calendar.getInstance();
       int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
       int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

       boolean isFridayBetween2PMAnd12AM = (dayOfWeek == Calendar.FRIDAY) && (hourOfDay >= 14 || hourOfDay == 0);
       model.addAttribute("isFridayBetween2PMAnd12AM", isFridayBetween2PMAnd12AM);


		return "adminOrder";
	}

	@PostMapping("/submitOrder")
	public String doorFile(@ModelAttribute("orderList") OrderList orderList, Model model,RedirectAttributes redirectAttributes) {
		System.out.println("OrderList" + orderList.getOrderList().toString());
System.out.println("my order linkkkkkkkkkkkkkkkkkk");
		OrderListEntity weekOrderList = new OrderListEntity();
		List<EmployeeEntity> nameList = employeeService.NameByRole();
		RestaurantEntity restData = serviceR.findById(orderList.getResId()).get();
		Integer peopleCount = 0;
		Integer totalPrice = 0;

		for (WeeklyOrderEntity weekOrder : orderList.getOrderList()) {
			peopleCount += weekOrder.getTotalPeople();
			totalPrice += weekOrder.getTotalPrice();

		}
		Date currentDate = new Date();
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String formattedDate = inputFormat.format(currentDate);
		// System.out.println("Date::----"+formattedDate);
		String startDate = orderList.getOrderList().get(0).getDate();
		String endDate = orderList.getOrderList().get(orderList.getOrderList().size()-1).getDate();
		System.out.println("order end date::::::::"+endDate);
		weekOrderList.setPrice(restData.getTotalPrice());
		weekOrderList.setStatus("unpaid");
		weekOrderList.setTotalPeople(peopleCount);
		weekOrderList.setTotalPrice(totalPrice);
		weekOrderList.setStartDate(startDate);
		weekOrderList.setDatPrice((int) restData.getPrice().getDatPrice());
    weekOrderList.setEmpPrice((int) restData.getPrice().getEmpPrice());
		weekOrderList.setEndDate(endDate);
		weekOrderList.setWeeklyOrderList(orderList.getOrderList());
		weekOrderList.setCreatedAt(formattedDate);
		weekOrderList.setRestaurantEntity(restData);
		weekOrderList.setIsDelete("Active");
		System.out.println(orderList.getOrderList());
		
		orderListService.save(weekOrderList);
//System.out.println("weekorder list : "+weekOrderList);
		//model.addAttribute("peopleCount", peopleCount);
		//model.addAttribute("totalPrice", totalPrice);
		//model.addAttribute("orderListEntity", orderListEntity);

		// System.out.print(endDate);

		//return "orderVouncher";

		redirectAttributes.addAttribute("orderSuccess", "true");


	return "redirect:/admin/weeklyOrderList";

	}


	@GetMapping("/admin/weeklyOrderList")
	public String userListPage(Model model) {
		String start = null;
		String end = null;
		String status=null;
		return findPagination(1, "id", "asc",start,end,status, model);
	}
	@GetMapping("/stuPage/{pageNo}")
	public String findPagination(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,@RequestParam(value = "start", required = false) String start,@RequestParam(value = "end", required = false) String end,@RequestParam(value = "status", required = false) String status,
			Model model) {
		System.out.println("ssssssssssssss:"+start);
		System.out.println("ssssssssssssss:"+end);
		int pageSize = 4;
		 if (start != null && end != null && !start.isEmpty() && !end.isEmpty()) {
		        if (isStartDateAfterEndDate(start, end)) {
		        	System.out.println("aaaaaaaaaaaa");
		            model.addAttribute("error", "Start date cannot be greater than end date");
		            return "WeeklyOrderList";
		        }
		       
		    }
		 else if((start != null && end == null && !start.isEmpty() && end.isEmpty()) ||(start == null && end != null && start.isEmpty() && !end.isEmpty()) ){
			 model.addAttribute("error","Please enter start date and end date");
			 return "WeeklyOrderList";
		 }
		Page<OrderListEntity> page = orderListService.findPagination(pageNo, pageSize, sortField, sortDir,start,end,status);
		List<OrderListEntity> orderList = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("start",start);
		model.addAttribute("end",end);
		model.addAttribute("status", status);
		model.addAttribute("pageSize", pageSize);
		
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("orderList", orderList);

		return "WeeklyOrderList";
	}
//	@GetMapping("/admin/weeklyOrderList")
//	public String displyOrderList(Model model) {
//		List<OrderListEntity> orderList=orderListService.getFindAll();
//		model.addAttribute("orderList", orderList);
//		
//		return "WeeklyOrderList";
//
//	}
	@GetMapping("/admin/deleteOrderList/{id}")
	public String displayDeleteOrder(@PathVariable("id")Integer id,Model model) {
		Optional<OrderListEntity> orderList=orderListService.getFindOne(id);
		if(orderList.isPresent()) {
			OrderListEntity order=orderList.get();
			order.setIsDelete("InActive");
			orderListService.save(order);
			
		}
		return "redirect:/admin/weeklyOrderList";
		
	}

	@GetMapping("/weeklyOrderDetail/{id}")
	public String showOrderVouncher(Model model,@PathVariable("id") Integer id,@RequestParam("vId") String vid) {
		System.out.println("vouncher::::"+vid);
		Optional<OrderListEntity> orderDetail=orderListService.getFindOne(id);
		System.out.println("nAMEMMMMMMM:"+orderDetail.get().getRestaurantEntity().getName());
		//RestaurantEntity restData = restaurantSer.getPriceByRestaurant();
		Integer peopleCount = 0;
		Integer totalPrice = 0;

		if(orderDetail.isPresent()) {
			model.addAttribute("orderDetail", orderDetail.get());
			peopleCount+=orderDetail.get().getTotalPeople();
			totalPrice+=orderDetail.get().getTotalPrice();
		}
		
		model.addAttribute("createdAt", orderDetail.get().getCreatedAt());
		model.addAttribute("peopleCount", peopleCount);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("vid", vid);
		model.addAttribute("id",id);
		model.addAttribute("resName", orderDetail.get().getRestaurantEntity().getName());
		//model.addAttribute("restData",restData);
		
		
		return "OrderVouncher";

	}
	@GetMapping("/admin/weeklyPaymentPage/{id}")
	public String displayWeekPaymentPage(Model model,@PathVariable("id") Integer id,@RequestParam("vId") String vid) {
		System.out.println("vouncher::::"+vid);
		Date currentDate = new Date();
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy ");
		 String formattedDate = inputFormat.format(currentDate);
		Optional<OrderListEntity> orderDetail=orderListService.getFindOne(id);
		List<EmployeeEntity> nameList = employeeService.NameByRole();
//		System.out.print("nnnnnnnnnn"+nameList);
//		for(EmployeeEntity name:nameList) {
//			model.addAttribute("name",name.getName());
//		}
		
		
		String startDate=orderDetail.get().getStartDate();
		String endDate=orderDetail.get().getEndDate();
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
		 
		
		 Date inputDate = null;
		 Date inputDate2=null;
		try {
			inputDate = input.parse(startDate);
			inputDate2 = input.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		SimpleDateFormat output = new SimpleDateFormat("d/MM/yyyy");
		String outputDateStart = output.format(inputDate);
		String outputDateEnd = output.format(inputDate2);
		

		Integer peopleCount = 0;
		Integer totalPrice = 0;

		if(orderDetail.isPresent()) {
			model.addAttribute("orderDetail", orderDetail.get());
			peopleCount+=orderDetail.get().getTotalPeople();
			totalPrice+=orderDetail.get().getTotalPrice();
		}
		
		model.addAttribute("createdAt", orderDetail.get().getCreatedAt());
		model.addAttribute("peopleCount", peopleCount);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("vid", vid);
		
		model.addAttribute("currentDate", formattedDate);
		model.addAttribute("sDate",outputDateStart);
		model.addAttribute("eDate",outputDateEnd);
		model.addAttribute("nameList", nameList);
		model.addAttribute("payment",new PaymentCashierBean());
		model.addAttribute("resName", orderDetail.get().getRestaurantEntity().getName());
		model.addAttribute("resReceived", orderDetail.get().getRestaurantEntity().getReceiveBy());
		
	
		
		return "WeeklyPaymentPage";

	}
	
	
	@PostMapping("/admin/paymentForm/{id}")
  public String displayPaymentConfirm(@PathVariable("id") Integer id,@RequestParam("vId") String vid,@ModelAttribute("payment") PaymentCashierBean payment,Model model,RedirectAttributes ra) {
    Optional<OrderListEntity> orderDetail=orderListService.getFindOne(id);
    if(orderDetail.isPresent()) {
      OrderListEntity orderPayment=orderDetail.get();
      orderPayment.setCashier(payment.getCashier());
      orderPayment.setReceivedBy(payment.getReceived());
      orderPayment.setApprovedManager(payment.getApproved());
      orderPayment.setPaymentDate(payment.getPaymentDate());
      orderPayment.setPaymentMethod(payment.getPaymentMethod());
      orderPayment.setStatus("paid");
      orderListService.save(orderPayment);
    }
    ra.addFlashAttribute("paymentSuccess","Weekly payment  successfully!");
    return "redirect:/admin/weeklyPaymentVouncher/"+id+"?vId="+vid;
    
  }


	@GetMapping("/admin/weeklyPaymentVouncher/{id}")
	public String displayPaymentVouncher(@PathVariable("id") Integer id,@RequestParam("vId") String vid,Model model) {
		Date currentDate = new Date();

		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy ");
		 String formattedDate = inputFormat.format(currentDate);
		Optional<OrderListEntity> orderDetail=orderListService.getFindOne(id);
		
		System.out.println("fin  one id :: "+orderDetail);
		List<EmployeeEntity> nameList = employeeService.NameByRole();
//		System.out.print("nnnnnnnnnn"+nameList);
//		for(EmployeeEntity name:nameList) {
//			model.addAttribute("name",name.getName());
//		}
		
		
		String startDate=orderDetail.get().getStartDate();
		String endDate=orderDetail.get().getEndDate();
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
		 
		
		 Date inputDate = null;
		 Date inputDate2=null;
		try {
			inputDate = input.parse(startDate);
			inputDate2 = input.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		SimpleDateFormat output = new SimpleDateFormat("d/MM/yyyy");
		String outputDateStart = output.format(inputDate);
		String outputDateEnd = output.format(inputDate2);
		

		Integer peopleCount = 0;
		Integer totalPrice = 0;

		if(orderDetail.isPresent()) {
			model.addAttribute("orderDetail", orderDetail.get());
			//System.out.print("eeeeeeeeee"+orderDetail.get().);
			peopleCount+=orderDetail.get().getTotalPeople();
			totalPrice+=orderDetail.get().getTotalPrice();
		}
		
		
		System.out.println("order detal res "+orderDetail);
		model.addAttribute("createdAt", orderDetail.get().getCreatedAt());
		model.addAttribute("peopleCount", peopleCount);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("vid", vid);
		model.addAttribute("id", id);
		model.addAttribute("currentDate", formattedDate);
		model.addAttribute("sDate",outputDateStart);
		model.addAttribute("eDate",outputDateEnd);
		model.addAttribute("nameList", nameList);
		model.addAttribute("resName", orderDetail.get().getRestaurantEntity().getName());
	
		return "WeeklyPaymentVouncher";
	}


	@SuppressWarnings("deprecation")
	@GetMapping("/report/monthlyOrderList")
	public void generateReport(HttpServletResponse response, @RequestParam("type") String type,
			@RequestParam(value = "start", required = false) String start,@RequestParam(value = "end", required = false) String end,@RequestParam(value = "status", required = false) String status) throws Exception {
		System.out.println("start--------"+start);
		List<OrderListEntity> list =new ArrayList<>();
		//int pageSize = 4;
		if (start != null && !start.isEmpty() && end != null && !end.isEmpty() && status != null && !status.isEmpty() ) {
			list=orderListService.findOrderListByThreereport(start, end, status);
			
			} else if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
			
			list=orderListService.monthlyDateList(start, end);
      }
	 else if (status != null && !status.isEmpty()) {
		list=orderListService.findOrderListByStatusreport(status);
	}
	 else {
		 list=orderListService.getFindAll();
	 }
		
		
		List<JasperOrderBean> newExport=new ArrayList<>();
		
		for(OrderListEntity order:list) {
			Integer sum=0;
			sum+=order.getTotalPrice();
			JasperOrderBean exportOrder = new JasperOrderBean();
			exportOrder.setId(order.getId());
			exportOrder.setStart_date(order.getStartDate());
			exportOrder.setEnd_date(order.getEndDate());
			exportOrder.setCashier(order.getCashier());
			exportOrder.setApproved_manager(order.getApprovedManager());
			exportOrder.setReceived_by(order.getReceivedBy());
			exportOrder.setPrice(order.getPrice());
			exportOrder.setTotal_people(order.getTotalPeople());
			exportOrder.setTotal_price(order.getTotalPrice());
			exportOrder.setStatus(order.getStatus());
			exportOrder.setPayment_date(order.getPaymentDate());
			exportOrder.setPayment_method(order.getPaymentMethod());
			exportOrder.setName(order.getRestaurantEntity().getName());
			exportOrder.setPhone(order.getRestaurantEntity().getPhone());
			exportOrder.setAddress(order.getRestaurantEntity().getAddress());
			exportOrder.setCreated_at(order.getCreatedAt());
			exportOrder.setTotalPriceSum(sum);
			newExport.add(exportOrder);
		}
		System.out.println("Export"+newExport);

		// compile the Jasper report template
		InputStream inputStream = (InputStream) getClass().getResourceAsStream("/reports/MonthlyPayment.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

		// create a JRBeanCollectionDataSource from the list of users
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(newExport);

		// set the parameters for the report
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("dataSource", dataSource);
		// generate the report as PDF and send it as response
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);

		if (type.equals("pdf")) {
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		} else {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=orderList.xls");
			// final OutputStream
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.exportReport();
		}

	}
////////////////////////////////////////////////////////////////DoorLog Start ///////////////////////////////////////////////////////////////////

		@GetMapping("/admin/doorlogcard")
		public String doorlogCard(Model model) {
			var listOfdate=doorlogService.selectDate();
		
			for(String x:listOfdate) {
				System.out.println("Date of doorlog "+x);	
			}
			model.addAttribute("dateOfArray", listOfdate);
			model.addAttribute("doorlogcard", new DoorLogEntity());
			return "admindoorlogcard";
		}

		@GetMapping("/admin/re")
		public String reList(HttpServletRequest request, @RequestParam("selectedDate") String selectedDate,
						Model model) {
			var listOfdate=doorlogService.selectDate();													
			var relist = dlRepo.findRE(selectedDate);
			System.out.print("Hello  :" + selectedDate);
			System.out.print(relist + " List");
			model.addAttribute("relist", relist);
			model.addAttribute("dateOfArray", listOfdate);
			return "adminRegisterEatList";

}

		@GetMapping("/admin/re1")
		public String handleFormSubmit(@RequestParam(name = "submitBtn", required = false) String submitBtn,
										@RequestParam("redate") String selectedDate, Model model,RedirectAttributes ra) {


											if(selectedDate == null || selectedDate.isEmpty()){
												model.addAttribute("dateEmpty", "Please,Choose Date!");
												ra.addFlashAttribute("dateEmpty","Please,Choose Date");
												return "redirect:/admin/doorlogcard";
											}

			var listOfdate=doorlogService.selectDate();		

		// 	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        //   DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //   LocalDate date = LocalDate.parse(selectedDate, inputFormatter);
        //   String outputDate = date.format(outputFormatter);

		// 	System.out.println("Re1 Date "+outputDate);
			if (submitBtn != null) {
			
			Integer reCount = lvRepo.findRE(selectedDate);
			System.out.print(selectedDate);
			System.out.print(reCount);
			model.addAttribute("reCount", reCount);
			model.addAttribute("date", selectedDate);
			model.addAttribute("dateOfArray", listOfdate);
			}

			return "admindoorlogcard";
			}

		@GetMapping("/admin/rne")
		public String rneList(HttpServletRequest request, @RequestParam("selectedDate") String selectedDate,
		Model model) {

			List<DoorLogViewEntity> rnelist = dlRepo.findRNE(selectedDate);
			System.out.print("Hello  :" + selectedDate);
			System.out.print(rnelist + " List");
			model.addAttribute("rnelist", rnelist);
			
			return "adminRegisterNoEat";
			
			}

		@GetMapping("/admin/rne1")
		public String handleFormSubmit2(@RequestParam(name = "submitBtn", required = false) String submitBtn,
										@RequestParam("rnedate") String selectedDate, Model model,RedirectAttributes ra) {

											if(selectedDate == null || selectedDate.isEmpty()){
												model.addAttribute("dateEmpty", "Please,Choose Date!");
												ra.addFlashAttribute("dateEmpty","Please,Choose Date");
												return "redirect:/admin/doorlogcard";
											}

			var listOfdate=doorlogService.selectDate();	

		// 	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        //   DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //   LocalDate date = LocalDate.parse(selectedDate, inputFormatter);
        //   String outputDate = date.format(outputFormatter);

			if (submitBtn != null) {
			
			Integer rneCount = lvRepo.findRNE(selectedDate);
			System.out.print(selectedDate);
			System.out.print(rneCount);
			model.addAttribute("rneCount", rneCount);
			model.addAttribute("rnedate", selectedDate);
			model.addAttribute("dateOfArray", listOfdate);
			}

			return "admindoorlogcard";
			}

		@GetMapping("/admin/ue")
		public String ueList(HttpServletRequest request, @RequestParam("selectedDate") String selectedDate,
		Model model) {

			List<DoorLogViewEntity> uelist = dlRepo.findUE(selectedDate);// list
			System.out.print("Hello  :" + selectedDate);
			System.out.print(uelist + " List");
			model.addAttribute("uelist", uelist);
			
			return "adminUnregisterEatList1";

		}

		@GetMapping("/admin/ue1")
		public String handleFormSubmit3(@RequestParam(name = "submitBtn", required = false) String submitBtn,
										@RequestParam("uedate") String selectedDate, Model model,RedirectAttributes ra) {

											if(selectedDate == null || selectedDate.isEmpty()){
												model.addAttribute("dateEmpty", "Please,Choose Date!");
												ra.addFlashAttribute("dateEmpty","Please,Choose Date");
												return "redirect:/admin/doorlogcard";
											}

			var listOfdate=doorlogService.selectDate();

		// 	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        //   DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //   LocalDate date = LocalDate.parse(selectedDate, inputFormatter);
        //   String outputDate = date.format(outputFormatter);


			if (submitBtn != null) {
			
			Integer ueCount = lvRepo.findUE(selectedDate);
			System.out.print(selectedDate);
			System.out.println("jkdfkdf :: "+(lvRepo.findUE(selectedDate) == null));
			System.out.print(ueCount);
			model.addAttribute("ueCount", ueCount);
			model.addAttribute("uedate", selectedDate);
			model.addAttribute("dateOfArray", listOfdate);
			}
			
			return "admindoorlogcard";
			}
	
		@GetMapping("/admin/replusue")
		public String replusueList(HttpServletRequest request, @RequestParam("selectedDate") String selectedDate,
		Model model,RedirectAttributes ra) {

			if(selectedDate == null || selectedDate.isEmpty()){
				model.addAttribute("dateEmpty", "Please,Choose Date!");
				ra.addFlashAttribute("dateEmpty","Please,Choose Date");
				return "redirect:/admin/doorlogcard";
			}

			List<DoorLogViewEntity> totalList = new ArrayList<>();
			List<DoorLogViewEntity> relist = dlRepo.findRE(selectedDate);
			List<DoorLogViewEntity> uelist = dlRepo.findUE(selectedDate);
			for (DoorLogViewEntity re : relist) {
			totalList.add(re);
			}
			for (DoorLogViewEntity ue : uelist) {
			totalList.add(ue);
			}
			System.out.print("Hello  :" + selectedDate);
			
			System.out.print(" List" + totalList);
			
			model.addAttribute("totalList", totalList);
			return "adminTotalAccessList";
			
			}

		@GetMapping("/admin/replusue1")
		public String handleFormSubmit4(@RequestParam(name = "submitBtn", required = false) String submitBtn,
		@RequestParam("totaldate") String selectedDate, Model model,RedirectAttributes ra) {
			if(selectedDate == null || selectedDate.isEmpty()){
				model.addAttribute("dateEmpty", "Please,Choose Date!");
				ra.addFlashAttribute("dateEmpty","Please,Choose Date");
				return "redirect:/admin/doorlogcard";
			}

			var listOfdate=doorlogService.selectDate();

		// 	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        //   DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //   LocalDate date = LocalDate.parse(selectedDate, inputFormatter);
        //   String outputDate = date.format(outputFormatter);

			if (submitBtn != null) {
			
			Integer reCount = lvRepo.findRE(selectedDate);
			Integer ueCount = lvRepo.findUE(selectedDate);
			
			int totalCount = reCount + ueCount;
			
			System.out.print(selectedDate);
			System.out.print(totalCount);
			
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("totaldate", selectedDate);
			model.addAttribute("dateOfArray", listOfdate);
			}
			return "admindoorlogcard";
			}

////////////////////////////////////////////////////////////////DoorLog End ///////////////////////////////////////////////////////////////////

		@GetMapping("/admin/feedbackList")
		public String displayFeedbackListPage(Model model) {
		List<FeedbackEntity> list=feedbackService.getFeedbackByActive();
		model.addAttribute("list", list);
		return "adminFeedbackList";
		
	}
	@GetMapping("/admin/deleteFeedback/{id}")
	public String displayDelete(@PathVariable("id") Integer id,Model model) {
		System.out.print(id);
	 Optional<FeedbackEntity> feedback= feedbackService.findById(id);
	 if(feedback.isPresent()) {
		 FeedbackEntity feed=feedback.get();
		 feedbackService.save(feed);
		 
	 }
		
		return "redirect:/admin/feedbackList";
		
	}
//	@GetMapping("/admin/lunchAllList")
//	public String displayLunchAllPage(Model model) {
//		return "adminLunchAllList";
//	}
	@GetMapping("/admin/lunchAllList")
	public String displayLunchAllPage(Model model) {
		String start = null;
		String end = null;
		String status=null;
		
		return findLunchAllPagination(1, "name", "asc",start,end,status, model);
	}
	@GetMapping("/lunchAllPage/{pageNo}")
	public String findLunchAllPagination(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,@RequestParam(value = "start", required = false) String start,@RequestParam(value = "end", required = false) String end,@RequestParam(value = "status", required = false) String status,
			Model model) {
		System.out.println("ssssssssssssss:"+start);
		System.out.println("ssssssssssssss:"+end);
		System.out.println("ssssssssssssss:"+status);
		int pageSize = 10;
		 if (start != null && end != null && !start.isEmpty() && !end.isEmpty()) {
		        if (isStartDateAfterEndDate(start, end)) {
		        	System.out.println("aaaaaaaaaaaa");
		            model.addAttribute("error", "Start date cannot be greater than end date");
		            return "adminLunchAllList";
		        }
		       
		    }
		 else if((start != null && end == null && !start.isEmpty() && end.isEmpty()) ||(start == null && end != null && start.isEmpty() && !end.isEmpty()) ){
			 model.addAttribute("error","Please enter start date and end date ");
			 return "adminLunchAllList";
		 }
		
			Page<DoorLogAllViewEntity> page = doorlogallViewSer.findLunchAllPagination(pageNo, pageSize, sortField, sortDir, start, end,status);
		
		
		
		List<DoorLogAllViewEntity> lunchList = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("start",start);
		model.addAttribute("end",end);
		model.addAttribute("status",status);
		model.addAttribute("pageSize", pageSize);
		
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("lunchList", lunchList);

		return "adminLunchAllList";
	}
	private boolean isStartDateAfterEndDate(String startDate, String endDate) {
	    try {
	        LocalDate start = LocalDate.parse(startDate);
	        LocalDate end = LocalDate.parse(endDate);
	        return start.isAfter(end);
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
	@SuppressWarnings("deprecation")
	@GetMapping("/report/doorlogAllList")
	public void generateReportDoorlog(HttpServletResponse response, @RequestParam("type") String type,
			@RequestParam(value = "start", required = false) String start,@RequestParam(value = "end", required = false) String end,@RequestParam(value = "status", required = false) String reg) throws Exception {
		System.out.println("start--------"+start);
		List<DoorLogAllViewEntity> list =new ArrayList<>();
		//int pageSize = 4;
		if (start != null && !start.isEmpty() && end != null && !end.isEmpty() && reg != null && !reg.isEmpty() ) {
			list=doorlogallViewSer.getDoorAllSearch(start, end, reg);
			
			} else if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
			
			list=doorlogallViewSer.getDoorAllSearchByStartEndreport(start, end);
      }
	 else if (reg != null && !reg.isEmpty()) {
		list=doorlogallViewSer.getDoorAllSearchByStatusreport(reg);
	}
	 else {
		 list=doorlogallViewSer.getDoorAllView();
	 }
		
		List<DoorlogReportBean> newExport=new ArrayList<>();
		
		for(DoorLogAllViewEntity door:list) {
			
		DoorlogReportBean exportDoor = new DoorlogReportBean(); 
			exportDoor.setStaff_id(door.getStaffId());
			exportDoor.setDoor_log(door.getKey().getDoorLogNo());
			exportDoor.setName(door.getName());
			exportDoor.setDate(door.getKey().getDate());
			exportDoor.setEat(door.getEat());
			
			exportDoor.setStatus(door.getStatus());
			newExport.add(exportDoor);
		}
		System.out.println("Export"+newExport);

		// compile the Jasper report template
		InputStream inputStream = (InputStream) getClass().getResourceAsStream("/reports/doorlogAll.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

		// create a JRBeanCollectionDataSource from the list of users
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(newExport);

		// set the parameters for the report
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("dataSource", dataSource);
		// generate the report as PDF and send it as response
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);

		if (type.equals("pdf")) {
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		} else {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=doorLog.xls");
			// final OutputStream
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.exportReport();
		}

	}
	  @GetMapping("/admin/doorlogList")
	  public String displayDoorlogList(Model model) {
		List<DoorLogEntity> doorlogList = doorlogService.getAllData();
		model.addAttribute("doorlogList", doorlogList);
		return "adminDoorlogList";
	  }
	
	  @GetMapping("/admin/AvoidMeat")
	  public String showAvoidMeatAdd(Model model) {
			  List<MeatList> AvoidMeatList = avoidms.getAllAvoidMeat();
		      
			    model.addAttribute("AvoidMeatList", AvoidMeatList);
	    model.addAttribute("avoidMeat",new MeatList());
	    return "adminAvoidMeat";
	  }
	  
	  
	  @PostMapping("/admin/doAvoidMeat")
	  public String saveAvoidMeat(@RequestParam("avoid") String avoidMeat,ModelMap model,RedirectAttributes ra) {
		  List<MeatList> AvoidMeatList = avoidms.getAllAvoidMeat();
	      
		    model.addAttribute("AvoidMeatList", AvoidMeatList);
	    
	    System.out.print("Successfully Added to databse !!!");
	    List<String> duAvoid=avoidms.duplicateAvoidMeat();
	    
	    var entity = new MeatList();
	    for(String data:duAvoid) {
	    	if(data.equals(avoidMeat)) {
	    		model.addAttribute("aa", avoidMeat);
	    		model.addAttribute("message","Avoid meat data is duplicate!");
	    		return "adminAvoidMeat";
	    	}
	    }
	    
	    entity.setMeat(avoidMeat);
	    entity.setIsDelete("Active");

		 avoidms.save(entity);
	      
	           System.out.print("Successfully Added to databse !!!");
	       System.out.print("Avoid Meat ::" + avoidMeat); 
	        
	       
	     model.addAttribute("avoidMeat",avoidMeat);
	     ra.addFlashAttribute("success","Avoid meat data insert is successfully!");
	 
	  
	    return "redirect:/admin/AvoidMeat";
	    
	  }

	  @GetMapping("/admin/updateAvoidMeat/{id}")
	  @ResponseBody
	  public ResponseEntity<String> updateAvoidMeat(@PathVariable("id") Integer id) {
	      // Perform the necessary logic to update avoidMeat with the given id
	      System.out.println("avoiddgkghn"+id);
		  try {
		        Optional<MeatList> avoid = avoidms.findById(id);
		        if (avoid.isPresent()) {
		            String avoidMeat = avoid.get().getMeat();
		            System.out.println("avoidMeat: " + avoidMeat);
		            return ResponseEntity.ok(avoidMeat);
		        }
		        return ResponseEntity.notFound().build(); // MeatList not found
		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Error retrieving Avoid Meat");
		    }
	  }
	  @PostMapping("/admin/setupupdateAvoidMeat")
	 
	  public ResponseEntity<String> setupUpdateAvoid(@RequestParam("id") Integer id,@RequestParam("meat") String meat){
		  Optional<MeatList> avoid = avoidms.findById(id);
		
		  if(avoid.isPresent()) {
			  MeatList list=avoid.get();
			 list.setMeat(meat);
			 avoidms.save(list);
		  }
		  String responseMessage = "Avoid meat  updated successfully.";
		    return ResponseEntity.ok(responseMessage);
	  }
	  







	  @GetMapping("/admin/deleteAvoidMeat/{id}")
	  public String deleteAvoidMeat(@PathVariable("id") Integer id,Model model) {
		  Optional<MeatList> avoidMeat=avoidms.findById(id);
			 if(avoidMeat.isPresent()) {
				 MeatList avoid=avoidMeat.get();
				 avoid.setIsDelete("InActive");
				 avoidms.save(avoid);
				 
			 }
			 return "redirect:/admin/AvoidMeat";
	  }
  
  
      @PostMapping
      public MeatList createAvoidMeat(@RequestBody MeatList avoidMeat) {
          
    	  MeatList createdAvoidMeat = avoidms.createAvoidMeat(avoidMeat);
          // Set the generated ID in the AvoidMeatEntity object
          avoidMeat.setId(createdAvoidMeat.getId());
          return createdAvoidMeat;
      }

     



  @GetMapping("/admin/EmployeeList")
  public String setEmployeeList(Model model) {
    
    List<EmployeeEntity> empList = employeeService.getAllEmployees();
    
    model.addAttribute("empList", empList);
    
    System.out.print("EmployeeList :: " + empList);
    return "adminEmployeeList";
  }
  
  @GetMapping("/admin/updateEmployeeList/{id}")
  @ResponseBody
  public ResponseEntity<EmployeeEntity> updateEmployeeList(@PathVariable("id") Long id) {
      // Perform the necessary logic to update avoidMeat with the given id
      System.out.println("employeeidddddddddddd"+id);
	  try {
	        Optional<EmployeeEntity> employee =employeeService.selectOne(id);
	        if (employee.isPresent()) {
	        	EmployeeEntity employeeList=employee.get();
	            
	            System.out.println("Employeeeeeeeeeeee: " +employeeList.toString());
	            return ResponseEntity.ok(employeeList);
	        }
	        return ResponseEntity.notFound().build(); // MeatList not found
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(null); // Error retrieving Employee
	    }
  }

  @PostMapping("/admin/seyupUpdateEmployee")
	 
  public ResponseEntity<String> setupUpdateEmployee(@ModelAttribute("employeeData") EmployeeEntity employee,HttpServletRequest session){
	
	System.out.println("updateeeeeeeeeeeeeeeee"+employee);
	  Optional<EmployeeEntity> emp = employeeService.selectOne(employee.getId());
		
	  if(emp.isPresent()) {
		  EmployeeEntity list=emp.get();
		 list.setStaffId(employee.getStaffId());
		 list.setDoorLog(employee.getDoorLog());
		 list.setName(employee.getName());
		 list.setEmail(employee.getEmail());
		 list.setDept(employee.getDept());
		 list.setDivision(employee.getDivision());
		 list.setTeam(employee.getTeam());
		employeeService.save(list);
		session.getSession().removeAttribute("name");
		session.getSession().setAttribute("name", list.getName());
	  }
	  

	  String responseMessage = "Employee data  updated successfully.";
	    return ResponseEntity.ok(responseMessage);
}

  
  @GetMapping("/admin/HolidayAdd")
  public String showHolidaysAdd(Model model) {
    model.addAttribute("holidays",new HolidaysEntity());
    return "adminHolidayAdd";
  }
  
  
  
  
  @GetMapping("/admin/doHolidayAdd")
  public String saveHoliday(@RequestParam("date") String date, @RequestParam("holidays") String holidays,ModelMap model,RedirectAttributes ra) {
    System.out.println("DSFBHSDGNR"+date);
    List<String>hDate=hdService.duplicateDate();
   // System.out.print("Successfully Added to databse !!!");
   

    boolean deletedatelist = hdService.deletedate(date);

	System.out.println("delet matched dates from date list table :: "+deletedatelist);





    HolidaysEntity entity = new HolidaysEntity();
    System.out.println(holidays);
    
    for(String dd:hDate) {
    	System.out.println("dfhjbhy"+dd);
    	if(date.equals(dd)) {
    		System.out.println("equal");
    		model.addAttribute("date", date);
    	    model.addAttribute("holiday", holidays);
    		model.addAttribute("errorDuplicate","Date is duplicate");
    		return "adminHolidayAdd";
    	}
    }
    entity.setDate(date);
    entity.setHolidays(holidays);

      hdRepo.save(entity);

      
           System.out.print("Successfully Added to databse !!!");
       System.out.print("Holidays ::" + holidays); 
        
      ra.addFlashAttribute("success","Holiday data insert is successfully!");   
      model.addAttribute("holidays",holidays);
     
 
  
    return "redirect:/admin/HolidayAdd";
    
  }

  @GetMapping("/admin/HolidaysList")
  public String setHolidaysList(Model model) {
     
    List<HolidaysEntity> holidaysList = hdService.getAllHolidays();
      
    model.addAttribute("hlist", holidaysList);
    
    System.out.print("Holidays ::" + holidaysList);
      return "adminHolidayList";
  }


  //sadfasdgasgasdgasdg
  
  @GetMapping("/admin/costList")
	public String costList(Model model) {
		String start = null;
		String end = null;
			
		return searchCost(1, "id", "asc",start,end, model);
		
		
	}
		  @GetMapping("/admin/searchCost/{pageNo}")
		    public String searchCost(     @PathVariable(value = "pageNo") int pageNo,
		                     @RequestParam("sortField") String sortField,
		                     @RequestParam("sortDir") String sortDir,
		                     @RequestParam(value = "start", required = false) String start,
		                     @RequestParam(value = "end", required = false) String end,
		                     Model m
		                      ) {
		      
		      List<CustomeCost> cost=new ArrayList<CustomeCost>();
		      List<WeeklyOrderEntity> a=new ArrayList<WeeklyOrderEntity>();
		            
		      System.out.println("ssssssssssssss:"+start);
		      System.out.println("ssssssssssssss:"+end);
		      System.out.println("ssssssssssssss:"+pageNo);
		      System.out.println("ssssssssssssss:"+sortField);
		      System.out.println("ssssssssssssss:"+sortDir);
		      int pageSize = 4; 

			  if(start != null && end != null && !start.isEmpty() && !end.isEmpty()){

				if(isStartDateAfterEndDate(start, end)){
					
					m.addAttribute("error","Start date cannot be greater than end date");
					return "adminCostList";
				}
			  }
			  else if(
				(start != null && end != null && !start.isEmpty() && !end.isEmpty()) || (start == null && end != null &&  start.isEmpty() && !end.isEmpty())
			  ){

				m.addAttribute("error", "Please enter start date and end date");
				return "adminCostList";
			  }


		      Page<WeeklyOrderEntity> page = weeklyOrderService.findPagination(pageNo, pageSize, sortField, sortDir,start,end);
		      List<WeeklyOrderEntity> b = page.getContent();
			  int count =0;
		      for(WeeklyOrderEntity week:b) {
		        CustomeCost cc= new CustomeCost();
		        Optional<LunchViewEntity> view=lvRepo.findByDate(week.getDate());
		        if(view.isPresent()) {
		          LunchViewEntity entity= view.get();
		        count = entity.getRe() + entity.getRne()+ entity.getUe();
		         cc.setEmpCostCount(count);		        
		         }
		        
		        cc.setDate(week.getDate());
		        cc.setDatPrice(week.getOrderList().getDatPrice());
		        cc.setPrice(week.getPrice());
		        cc.setTotalPeople(week.getTotalPeople());
		      	cc.setEaten(count);
		        cc.setEmpPrice(week.getOrderList().getEmpPrice());
		        cost.add(cc);
		      }
		        
		      m.addAttribute("costList", cost);
		      m.addAttribute("currentPage", pageNo);
		      m.addAttribute("totalPages", page.getTotalPages());
		      m.addAttribute("totalItems", cost.size());
		      m.addAttribute("sortField", sortField);
		      m.addAttribute("sortDir", sortDir);
		      m.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		      return "adminCostList";  
		    }
		    
		    
		    @SuppressWarnings("deprecation")
		    @GetMapping("/report/CostList")
		    public void costReport(HttpServletResponse response, @RequestParam("type") String type,
		        @RequestParam(value = "start", required = false) String start,@RequestParam(value = "end", required = false) String end) throws Exception {
		      List<WeeklyOrderEntity> list =new ArrayList<>();
		      if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
		        list=weeklyOrderService.searchEngine(start, end);
		      } else {
		        list =weeklyOrderService.findWeeklyReport();
		      }
		      
		      List<ReportCost> newExport=new ArrayList<>();
		      double dat=0.0;
		      double emp=0.0;
		      double restaurant=0.0;
		        for(WeeklyOrderEntity week : list) {
		        	int count = 0;
		        	Optional<LunchViewEntity> view=lvRepo.findByDate(week.getDate());
			        if(view.isPresent()) {
			          LunchViewEntity entity= view.get();
			          count = entity.getRe() + entity.getRne()+ entity.getUe();
			         }
		        	double aa=week.getTotalPeople() * week.getOrderList().getRestaurantEntity().getPrice().getDatPrice();
		        	double bb=count * week.getOrderList().getRestaurantEntity().getPrice().getEmpPrice();
		        	double cc=week.getTotalPeople() * week.getTotalPrice();
		        	
		        	dat+=aa;
		        	emp+=bb;
		        	restaurant+=cc;
			        ReportCost cost=new ReportCost();
			        
		          cost.setDate(week.getDate());
		          cost.setDatPrice(week.getOrderList().getDatPrice());
		          cost.setEmpPrice(week.getOrderList().getEmpPrice());
		          cost.setDatTotalPrice(week.getTotalPeople() * week.getOrderList().getRestaurantEntity().getPrice().getDatPrice());
		          cost.setEmpTotalPrice(count * week.getOrderList().getRestaurantEntity().getPrice().getEmpPrice());
		          cost.setPrice(week.getPrice());
		          cost.setResTotalPrice(week.getPrice() * week.getTotalPeople());
		          cost.setSumDATTotalPrice(dat);
		          cost.setSumEmpTotalPrice(emp);
		          cost.setSumSummaryTotalPrice(restaurant);
		          cost.setTotalPeople(week.getTotalPeople());
		          
		          newExport.add(cost);
		        }
		      
		      System.out.println("Export"+newExport);

		      // compile the Jasper report template
		      InputStream inputStream = (InputStream) getClass().getResourceAsStream("/reports/costList.jrxml");
		      JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

		      // create a JRBeanCollectionDataSource from the list of users
		      JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(newExport);

		      // set the parameters for the report
		      Map<String, Object> parameters = new HashMap<>();
		      parameters.put("dataSource", dataSource);
		      // generate the report as PDF and send it as response
		      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);

		  if (type.equals("pdf")) {
		        response.setContentType("application/pdf");
		        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		      } else {
		        response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=book.xls");
		        // final OutputStream
		        JRXlsExporter exporterXLS = new JRXlsExporter();
		        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		        exporterXLS.exportReport();
		      }

		    }
	@PostMapping("/admin/announce")
	public String announce(@ModelAttribute("annbean")Announce ann, Model m,RedirectAttributes ra) {
		AnnounceEntity entity=new AnnounceEntity();
		String fileName=null;
		if(ann.getImage().isEmpty()) {
			entity.setImage("logo.png");
		}else {
			MultipartFile multipartFile = ann.getImage();
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
				entity.setImage(fileName);
			}catch(Exception e) {
				System.out.println("file input error"+e.getMessage());
			}
		}
		
			LocalDate currentDate = LocalDate.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		    String isoDate = currentDate.format(formatter);
		    
		    
		    String dateTimeString = ann.getDate() + " " + ann.getTime();
	        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter2);
	        System.out.println("Date and Time "+dateTime);
		    
		    
			entity.setSubject(ann.getSubject());
			entity.setContant(ann.getContent());
			entity.setDateTime(dateTime);
			entity.setDateNow(isoDate);
			announceRepo.save(entity);
			ra.addFlashAttribute("success", "Announance post is successfully!");
		return "redirect:/admin/dashboard";
	}
  
	@GetMapping("/admin/setEmployeeStatus/{id}/{status}/{profileId}")
	public String setEmployeeStatus(@PathVariable("id")long id,
							@PathVariable("status")String status,
							@PathVariable("profileId")long pid,
							Model m){
		
		System.out.println("Account owner Id ==> "+pid);
		System.out.println("Set Status Method "+id+"=>"+status);
		Optional<EmployeeEntity> op1= employeeService.selectOne(id);
		Optional<EmployeeEntity> op= employeeService.selectOne(pid);
		if(op.isPresent() && op.isPresent()) {
			EmployeeEntity p=op.get();
			EmployeeEntity entity=op1.get();
			if(p.getRole().equals("ADMIN") && p.getId()==1) {
				if(entity.getRole().equals("ADMIN") || entity.getRole().equals("USER")) {
					System.out.println("Status in default ==> "+entity.getStatus());
					if(entity.getStatus().equals("Active")) {
						System.out.println("ACTIVE TO INACTIVE IN DEFAULT ADMIN");
						employeeService.updateA("InActive", id);
					}else{
						System.out.println("INACTIVE TO ACTIVE IN DEFAULT ADMIN");
						employeeService.updateA("Active", id);
					}
				}
				
			}else if(p.getRole().equals("ADMIN")) {
				if(entity.getRole().equals("USER")) {
					System.out.println("Status in normal ==> "+entity.getStatus());
					if(entity.getStatus().equals("Active")) {
						System.out.println("ACTIVE TO INACTIVE IN NORMAL ADMIN");
						employeeService.updateA("InActive", id);
					}else{
						System.out.println("INACTIVE TO ACTIVE IN NORMAL ADMIN");
						employeeService.updateA("Active", id);
					}
				}else {
					System.out.println("AUTHORITY DENINED!");
					m.addAttribute("errorAdmin", "You don't have authority to access!");
				}
				
			}else {
				
			}
		}
		
		
		return "redirect:/admin/EmployeeList";
	}
	
	@GetMapping("/admin/setRole/{id}/{role}/{profileId}")
	public String setEmployeeRole(@PathVariable("id")long id,
							@PathVariable("role")String role,
							@PathVariable("profileId")long pid,
							Model m){
		
		System.out.println("Account owner Id ==> "+pid);
		System.out.println("Set Status Method "+id+"=>"+role);
		Optional<EmployeeEntity> op1= employeeService.selectOne(id);
		Optional<EmployeeEntity> op= employeeService.selectOne(pid);
		if(op.isPresent() && op.isPresent()) {
			EmployeeEntity p=op.get();
			EmployeeEntity entity=op1.get();
			if(p.getRole().equals("ADMIN") && p.getId()==1) {
				if(entity.getRole().equals("ADMIN") || entity.getRole().equals("USER")) {
					System.out.println("Status in default ==> "+entity.getStatus());
					if(entity.getRole().equals("ADMIN")) {
						System.out.println("ACTIVE TO INACTIVE IN DEFAULT ADMIN");
						employeeService.updateB("USER", id);
					}else{
						System.out.println("INACTIVE TO ACTIVE IN DEFAULT ADMIN");
						employeeService.updateB("ADMIN", id);
					}
				}
				
			}else if(p.getRole().equals("ADMIN")) {
				if(entity.getRole().equals("USER")) {
					System.out.println("Status in normal ==> "+entity.getStatus());
					if(entity.getRole().equals("ADMIN")) {
						System.out.println("ACTIVE TO INACTIVE IN NORMAL ADMIN");
						employeeService.updateB("USER", id);
					}else{
						System.out.println("INACTIVE TO ACTIVE IN NORMAL ADMIN");
						employeeService.updateB("ADMIN", id);
					}
				}else {
					System.out.println("AUTHORITY DENINED!");
					m.addAttribute("errorAdmin", "You don't have authority to access!");
				}
				
			}else {
				
			}
		}
		
		
		return "redirect:/admin/EmployeeList";
	}



	@GetMapping("/admin/updateHoliday/{id}")
	  @ResponseBody
	  public ResponseEntity<HolidaysEntity> updateHolidays(@PathVariable("id") Integer id, HttpServletRequest req) {
	      System.out.println("avoiddgkghn"+id);
	      System.out.println("SUCCESS");
	      var listOfHolidays=hdService.getDate();
	      for(String a:listOfHolidays) {
	    	  System.out.println("ASDFASDF "+a);
	      }
	      req.getSession().setAttribute("listOfHolidays", listOfHolidays);
		  try {
		        Optional<HolidaysEntity> holi = hdService.findById(id);
		        if (holi.isPresent()) {
		           HolidaysEntity entity = holi.get();
		            return ResponseEntity.ok(entity);
		           
		        }
		        return ResponseEntity.notFound().build();
		    } catch (Exception e) {
		    	 e.printStackTrace();
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			                .body(null);
		    }
	  }
	 
	  @PostMapping("/admin/holidaysForm")
	
	  public ResponseEntity<String> doUpdateHolidays(HttpServletRequest req,@RequestParam("id") long id,@RequestParam("date") String date, @RequestParam("holidays")String holidays){
		  Optional<HolidaysEntity> holi = hdService.findById(id);
		
		  if(holi.isPresent()) {
			 HolidaysEntity list=holi.get();
			 list.setDate(date);
			 list.setHolidays(holidays);
			 hdService.saveHoliday(list);
		  }
		  String responseMessage = "Successfully updated !";
		  req.getSession().removeAttribute("listOfHolidays");
		  var listOfHolidays=hdService.getDate();
		  for(String a:listOfHolidays) {
	    	  System.out.println("ASDFASDF "+a);
	      }
		  req.getSession().setAttribute("listOfHolidays", listOfHolidays);
		    return ResponseEntity.ok(responseMessage);
	  }



	 
	  @GetMapping("/admin/deleteHoliday/{id}")
	  public String deleteHoliday(@PathVariable("id") long id,Model model) {
		  if(id != 0) {
			  hdService.deleteById(id);
		  }
			 return "redirect:/admin/HolidaysList";
	  }


}


























