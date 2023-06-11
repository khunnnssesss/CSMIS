package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dat.csmis.entity.WeeklyHistory;
import com.dat.csmis.model.WeeklyPrice;

public interface WeekLyHistoryRepo extends JpaRepository<WeeklyHistory,Integer>{
    
    @Query(value = "select new com.dat.csmis.model.WeeklyPrice(d.resId,d.price,d.datp,d.empp) from WeeklyHistory d where d.weekst between :str and :end")
    WeeklyPrice empPP(@Param("str") String startd,@Param("end") String endd);
}
