package com.dat.csmis.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.model.WeeklyLists;
import com.dat.csmis.repository.DateListEntityRepository;

@Service
public class DateListEntityService {
	@Autowired
	DateListEntityRepository dateListRepository;

	public List<Object[]> getNextWeekRegister() {
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
		List<Object[]> list = dateListRepository.findNextWeekDates(startDate, endDate);
		return list;
	}

	public List<String> getnext(String empid,String startdate,String enddate){

		var listsd = dateListRepository.findNextWeekDatesLists(startdate, enddate,empid);

		return listsd;
	}

public boolean getMatchdate(String date){

	try {
		dateListRepository.deleteDates(date);
		return true;
	} catch (Exception e) {
		// TODO: handle exception
		return false;
	}


}

}
