package com.dat.csmis.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.LunchViewEntity;
import com.dat.csmis.repository.lunchViewRepository;



@Service
public class lunchViewService {
	@Autowired 
	lunchViewRepository lunchviewRepository;
	//doorlog view in dashboard
		public List<LunchViewEntity> getCurrentWeekDoorlogAccess() {

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
			List<LunchViewEntity> list =lunchviewRepository.findCurrentDoorlogAccess(startDate, endDate);
			return list;
		}
}
