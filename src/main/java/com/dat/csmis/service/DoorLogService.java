package com.dat.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.DoorLogEntity;
import com.dat.csmis.repository.DoorLogRepository;

@Service	
public class DoorLogService {
@Autowired 
DoorLogRepository doorlogRepository;



public int doorLogCountByCurrentdate() {
	return doorlogRepository.countEntriesForCurrentDate();
}

public List<DoorLogEntity> getAllData(){
	return doorlogRepository.findAll();
  }
public List<String> selectDate(){
	return doorlogRepository.selectDate();
}

}
