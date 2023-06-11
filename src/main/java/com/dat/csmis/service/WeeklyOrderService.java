package com.dat.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.WeeklyOrderEntity;
import com.dat.csmis.repository.WeeklyOrderRepository;
@Service
public class WeeklyOrderService {
	@Autowired
	WeeklyOrderRepository weekOrderRepo;
	public WeeklyOrderEntity save(WeeklyOrderEntity weeklyOrder){
		WeeklyOrderEntity weeklyList= weekOrderRepo.save(weeklyOrder);
		return weeklyList;
	}
	public List<WeeklyOrderEntity> getAllList(){
		List<WeeklyOrderEntity> weeklyList= weekOrderRepo.findAll();
		return weeklyList;
	}
	public List<WeeklyOrderEntity> findWeeklyReport(){
		List<WeeklyOrderEntity> weeklyList= weekOrderRepo.findWeeklyListReport();
		return weeklyList;
	}
	public List<WeeklyOrderEntity> searchEngine(String start, String end){
		return weekOrderRepo.searchEngineReport(start, end);
	}
	public Page<WeeklyOrderEntity> findPagination(int pageNo,int pageSize,String sortField,String sortDirection,String start,String end) {
	       
	       Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending(): Sort.by(sortField).descending();
	    Pageable pageable=PageRequest.of(pageNo - 1, pageSize,sort);
	    if(start!= null && end != null) {
	      return weekOrderRepo.findAllO(pageable, start, end);
	    }
	    
	       return weekOrderRepo.findAll(pageable);
	  }
}
