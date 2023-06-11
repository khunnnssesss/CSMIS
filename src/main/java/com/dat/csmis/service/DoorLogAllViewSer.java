package com.dat.csmis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.DoorLogAllViewEntity;
import com.dat.csmis.entity.EmpDoorlog;
import com.dat.csmis.entity.OrderListEntity;
import com.dat.csmis.entity.YourEntityKey;
import com.dat.csmis.repository.DoorlogAllViewRepo;
import com.dat.csmis.repository.DoorlogViewRepository;

@Service
public class DoorLogAllViewSer {
	@Autowired
	DoorlogAllViewRepo doorlogallViewRepo;
	@Autowired DoorlogViewRepository doorlogRepository;

	public Page<DoorLogAllViewEntity> findLunchAllPagination(int pageNo, int pageSize, String sortField,
			String sortDirection, String start, String end, String status) {

		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		if (start != null && !start.isEmpty() && end != null && !end.isEmpty() && status != null && !status.isEmpty()) {
			return doorlogallViewRepo.findAllDoor(pageable, start, end, status);
		} else if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
			return doorlogallViewRepo.findAllDoor(pageable, start, end);
		} else if (status != null && !status.isEmpty()) {
			return doorlogallViewRepo.findAllDoor(pageable, status);
		} else {
			return doorlogallViewRepo.findAll(pageable);
		}

	}

	public List<DoorLogAllViewEntity> getDoorAllView() {
		List<DoorLogAllViewEntity> list = doorlogallViewRepo.getDoorLogAllView();
		return list;
	}

	public List<DoorLogAllViewEntity> getDoorAllSearch(String start, String end, String status) {
		List<DoorLogAllViewEntity> list = doorlogallViewRepo.getDoorLogSearch(start, end, status);
		return list;
	}
	public List<DoorLogAllViewEntity> getDoorAllSearchByStartEndreport(String start, String end) {
		List<DoorLogAllViewEntity> list = doorlogallViewRepo.findAllDoorByStartEndreport(start, end);
		return list;
	}
	public List<DoorLogAllViewEntity> getDoorAllSearchByStatusreport(String status) {
		List<DoorLogAllViewEntity> list = doorlogallViewRepo.findAllDoorByStatusreport(status);
		return list;
	}

	public List<Object[]> getCountRegisterByMonth() {
		return doorlogallViewRepo.getCountsByMonth();
	}

		
	public List<EmpDoorlog> getEmpRegisterList(String doorlog,String datestart,String dateend){
		var empd = new ArrayList<EmpDoorlog>();
		var getData = doorlogRepository.getDoorlogWeeklyLists(doorlog, datestart, dateend);
		System.out.println("in list emp doorl og :: "+getData);
		// for (DoorLogViewEntity dla : getData) {
		// 	var emp = new EmpDoorlog(dla.getDate(),dla.getEat(),dla.getStatus());
		// 	empd.add(emp);
		// }

		return empd;
	}


	public List<EmpDoorlog> getUserMonthlyHistory(String doorLogNo,String month){

		return doorlogRepository.getDoorlogMonthlyLists(doorLogNo, month);
	}
	
}
