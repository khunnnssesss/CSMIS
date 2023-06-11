package com.dat.csmis.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.dat.csmis.entity.HolidaysEntity;

public interface HolidaysRepository extends JpaRepository<HolidaysEntity,Long>{
	@Query("SELECT DISTINCT h.date FROM HolidaysEntity h")
	List<String> getDistinctDates();
	@Query("select distinct h from HolidaysEntity h where h.date = ?1")
	  public Optional<HolidaysEntity> findByDate(String date);
	  
	  @Query("update HolidaysEntity h set h.date= ?1, h.holidays= ?2 where h.id= ?3")
	  @Modifying
	  @Transactional
	  public void updateHoli(String date, String holidays, long id);

	  @Modifying
	  @Transactional
	  @Query("delete from DateListEntity d where d.date = ?1")
	  public void deleteDates(String date);

	  @Query("select h.date from HolidaysEntity h")
	  public List<String> getDate();
}
