package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dat.csmis.entity.DailyEatView;
import com.dat.csmis.model.DailyDatePrice;
import com.dat.csmis.model.DailyRegister;


public interface DailyEatViewRepo extends JpaRepository<DailyEatView,String>{

    @Query(value = "select new com.dat.csmis.model.DailyRegister(de.date,de.eat,de.status,de.datPrice,de.empPrice) from DailyEatView de where de.doorlog = :dlg and de.date like :mth%")
    List<DailyRegister> getUser(@Param("dlg") String doorlog,@Param("mth") String month);
    
    @Query(value = "select new com.dat.csmis.model.DailyRegister(de.date,de.eat,de.status,de.datPrice,de.empPrice) from DailyEatView de where de.staffId = :stid")
    List<DailyRegister> getAllOne(@Param("stid") String staffid);

    @Query(value ="SELECT new com.dat.csmis.model.DailyDatePrice(det.date,det.status,det.eat,det.empPrice) FROM DailyEatView det where det.staffId= :stid and det.date like :month% order by date")
    List<DailyDatePrice> getmonthlydates(@Param("stid") String userid,@Param("month") String monthdate);
}
