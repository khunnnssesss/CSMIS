package com.dat.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.model.DailyDatePrice;
import com.dat.csmis.model.DailyRegister;
import com.dat.csmis.repository.DailyEatViewRepo;


@Service
public class DailyEatViewService {
    
    @Autowired private DailyEatViewRepo dailyEatViewRepo;


    public List<DailyRegister> getAll(String userid){

    return dailyEatViewRepo.getAllOne(userid);
    }

    public List<DailyRegister> getOne(String doorlog,String month){

        return dailyEatViewRepo.getUser(doorlog, month);
    }

    public List<DailyDatePrice> getMonthlyData(String userid,String month){

		var monthlists = dailyEatViewRepo.getmonthlydates(userid,month);
		return monthlists;
	}
}
