package com.dat.csmis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.model.WeeklyPrice;
import com.dat.csmis.repository.WeekLyHistoryRepo;

@Service
public class WeeklyHistoryService {
    
    @Autowired private WeekLyHistoryRepo weekLyHistoryRepo;


    public WeeklyPrice getWeekPrice(String str,String end){

        return weekLyHistoryRepo.empPP(str, end);
    }
}
