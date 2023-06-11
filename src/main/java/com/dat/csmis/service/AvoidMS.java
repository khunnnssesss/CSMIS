package com.dat.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.AvoidMeatEntity;
import com.dat.csmis.entity.MeatList;
import com.dat.csmis.repository.AvoidMSP;

@Service
public class AvoidMS {

  
	 @Autowired
	    private AvoidMSP avoidMeatRepository;
	  public Optional<MeatList> findById(int id) {
		  Optional<MeatList> list=avoidMeatRepository.findById(id);
		  return list;
	  }

	    public MeatList createAvoidMeat(MeatList avoidMeat) {
	        return avoidMeatRepository.save(avoidMeat);
	    }

	  
	    public List<MeatList> getAllAvoidMeat() {
	        return avoidMeatRepository.allList();
	    }

	  
	    public void deleteAvoidMeat(int id) {
	        avoidMeatRepository.deleteById(id);
	    }

	    public int update(MeatList user,int i)
	    {
	      int rs = 0;
	      avoidMeatRepository.save(user);
	      rs = 1;
	      return rs;
	    } 
	    
	    public void save(MeatList meat) {
	    
	    	avoidMeatRepository.save(meat);
	    }
	    
	    public int update(Integer id,MeatList avoidMeat) {
	        MeatList updatedAvoidMeat = avoidMeatRepository.save(avoidMeat);
	        return updatedAvoidMeat != null ? 1 : 0;
	    }
	    public List<String> duplicateAvoidMeat(){
	    	List<String> avoidMeat=avoidMeatRepository.getAvoidMeatDuplicate();
	    	return avoidMeat;
	    }
}