package com.dat.csmis.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.LunchViewEntity;

public interface lunchViewRepository extends JpaRepository<LunchViewEntity,String> 
{
   @Query(value = "select re from lunch_view where date = ?;", nativeQuery = true)
    Integer findRE(String date);
   
   @Query(value = "select rne from lunch_view where date = ?;", nativeQuery = true)
   Integer findRNE(String date);
   
   @Query(value = "select ue from lunch_view where date = ?;", nativeQuery = true)
   Integer findUE(String date);
   
   @Query(value = "select * from lunch_view where date = ?;", nativeQuery = true)
   Optional<LunchViewEntity> findByDate(String date);

   @Query(value="select * from lunch_view  where date >= ? and date <= ?;",nativeQuery=true)
   List<LunchViewEntity> findCurrentDoorlogAccess(String start,String end);
}
