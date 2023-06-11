package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dat.csmis.entity.DateListEntity;
import com.dat.csmis.model.WeeklyLists;

public interface DateListEntityRepository extends JpaRepository<DateListEntity,Integer>{
	
    @Query("SELECT d.date,COUNT(d) FROM DateListEntity d WHERE d.date >=?1  AND d.date <=?2 GROUP BY d.date")
    List<Object[]> findNextWeekDates(String startDate,String endDate);
    
    @Query(value = "SELECT d.date FROM date_list_entity d WHERE d.employee_id = ?3 and d.date >=?1  AND d.date <=?2 GROUP BY d.date",nativeQuery = true)
    List<String> findNextWeekDatesLists(String startDate,String endDate,String empid);

    @Modifying
    @Query("delete from DateListEntity d where d.date = :dt")
    public void deleteDates(@Param("dt") String date);
    
    

}
