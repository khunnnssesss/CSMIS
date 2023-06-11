package com.dat.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.HolidaysEntity;
import com.dat.csmis.repository.HolidaysRepository;

@Service
public class HolidayService {
    
    @Autowired private HolidaysRepository holidayrepo;

    public List<HolidaysEntity> getAll(){

        return holidayrepo.findAll();
    }

    
    public void saveHoliday(HolidaysEntity holidays) {
        holidayrepo.save(holidays);
    }

    public List<HolidaysEntity> getAllHolidays() {
        List<HolidaysEntity> holidaysList = holidayrepo.findAll();
        // Add debug log
        System.out.println("Retrieved holidays from database: " + holidaysList);
        return holidaysList;
    }
    public List<String> duplicateDate(){
    	List<String> hDate=holidayrepo.getDistinctDates();
    	return hDate;
    }

    public boolean deletedate(String date){
        try {
            holidayrepo.deleteDates(date);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public Optional<HolidaysEntity> findById(long id){
    	return holidayrepo.findById(id);
    }
    public void deleteById(long id) {
    	holidayrepo.deleteById(id);
    }
    public List<String> getDate(){
    	return holidayrepo.getDate();
    }
    
}
