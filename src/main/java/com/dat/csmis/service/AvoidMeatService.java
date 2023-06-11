package com.dat.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.repository.AvoidMeatRepository;

@Service
public class AvoidMeatService {

@Autowired AvoidMeatRepository avoidMeatRepository;

public List<Object[]> avoidMeatCount(){
	return avoidMeatRepository.getCountByAvoid();
}

// public MeatListEntity createAvoidMeat(MeatListEntity avoidMeat) {
// 	return avoidMeatRepository.save(avoidMeat);
// }

}
