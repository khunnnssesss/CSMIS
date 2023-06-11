package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.MeatList;

public interface MeatListRepo extends JpaRepository<MeatList,Integer>{
    

    @Query("select e from MeatList e where e.isDelete = 'Active'")
   List<MeatList> getallmeatlists();
}
