package com.dat.csmis.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.OrderListEntity;
import com.dat.csmis.repository.OrderListRepository;
@Service
public class OrderListService {
	@Autowired 
	OrderListRepository orderListRepo;
	public OrderListEntity save(OrderListEntity order) {
		OrderListEntity list=orderListRepo.save(order);
		return list;
		
	}
	public List<OrderListEntity> getFindAll() {
		List<OrderListEntity> list=orderListRepo.findAll();
		return list;
		
	}
	public Optional<OrderListEntity> getFindOne(Integer id) {
		Optional<OrderListEntity> list=orderListRepo.findById( id);
		return list;
		
	}
	public Page<OrderListEntity> findPagination(int pageNo,int pageSize,String sortField,String sortDirection,String start,String end,String status) {
		   
		   Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending(): Sort.by(sortField).descending();
		Pageable pageable=PageRequest.of(pageNo - 1, pageSize,sort);
		if (start != null && !start.isEmpty() && end != null && !end.isEmpty() && status != null && !status.isEmpty()) {
			return orderListRepo.findAllOrderBythree(pageable, start, end, status);
		} else if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
			return orderListRepo.findAllO(pageable, start, end);
		} else if (status != null && !status.isEmpty()) {
			return orderListRepo.findAllOrderByStatus(pageable, status);
		} else {
			return orderListRepo.findAll(pageable);
		}
	}
	public List<OrderListEntity> monthlyDateList(String start,String end){
		List<OrderListEntity> list=orderListRepo.monthlyListByDate(start, end);
		return list;
		
	}
	public OrderListEntity getCurrentWeekOrderList() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Calendar calendar = Calendar.getInstance();
		//System.out.println("Now: " + calendar.getTime());
		calendar.add(Calendar.WEEK_OF_YEAR,0);
		//System.out.println("Next Week: " + calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		System.out.println("Next Week Monday: " + calendar.getTime());
		String startDate = dateFormat.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		System.out.println("Next Week FRIDAY: " + calendar.getTime());
		String endDate = dateFormat.format(calendar.getTime());
		OrderListEntity list = orderListRepo.findCurrentWeekOrderList(startDate, endDate);
		return list;
	}
	public int getNextWeekRegisterOrderCount() {
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
		int list = orderListRepo.findNextWeekRegisterOrderCount(startDate, endDate);
		return list;
	}
	public Integer getNextWeekRegisterOrderByID() {
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
		Integer orderListId = orderListRepo.findNextWeekRegisterOrderListByID(startDate, endDate);
		return orderListId;
	}
	public String getNextWeekRegisterOrderByEndDate() {
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
		String orderEndDate = orderListRepo.findNextWeekRegisterOrderListByEndDate(startDate, endDate);
		return orderEndDate;
	}
	
	public Page<OrderListEntity> findPaginationCost(int pageNo,int pageSize,String sortField,String sortDirection,String start,String end) {
		   
		   Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending(): Sort.by(sortField).descending();
		Pageable pageable=PageRequest.of(pageNo - 1, pageSize,sort);
		if(start!=null && end!=null) {
			return orderListRepo.findAllO(pageable, start, end);
		}
		else {
			return orderListRepo.findAll(pageable);
		}
	}
	
	public List<OrderListEntity> findOrderListByStatusreport(String status){
		return orderListRepo.findAllOrderByStatusReport(status);
	}
	public List<OrderListEntity> findOrderListByThreereport(String start,String end,String status){
		return orderListRepo.findAllOrderBythreereport(start,end,status);
	}
	
	
}
