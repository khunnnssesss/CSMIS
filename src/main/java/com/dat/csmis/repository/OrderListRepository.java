package com.dat.csmis.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.OrderListEntity;


public interface OrderListRepository extends JpaRepository<OrderListEntity,Integer>{

	OrderListEntity save(OrderListEntity order);
	@Query("SELECT o FROM OrderListEntity o WHERE o.isDelete='Active'")
	List<OrderListEntity> findAll();
	
	@Query("SELECT o FROM OrderListEntity o WHERE o.isDelete='Active'")
	public Page<OrderListEntity> findAll(Pageable pageable);
	
	@Query("SELECT o from OrderListEntity o WHERE  (o.startDate BETWEEN ?1 AND ?2 OR o.endDate BETWEEN ?1 AND ?2) AND o.status=?3 AND (o.isDelete='Active') ")
	public Page<OrderListEntity> findAllOrderBythree(Pageable pageable,String start,String end,String status);
	
	@Query("SELECT o from OrderListEntity o WHERE  (o.startDate BETWEEN ?1 AND ?2 OR o.endDate BETWEEN ?1 AND ?2) AND (o.isDelete='Active') ")
	public Page<OrderListEntity> findAllO(Pageable pageable,String start,String end);
	
	@Query("SELECT o from OrderListEntity o WHERE  o.status=?1 AND o.isDelete='Active' ")
	public Page<OrderListEntity> findAllOrderByStatus(Pageable pageable,String status);
	  

	@Query("SELECT o from OrderListEntity o WHERE  (o.startDate BETWEEN ?1 AND ?2 OR o.endDate BETWEEN ?1 AND ?2) AND o.status=?3 AND (o.isDelete='Active') ")
	public List<OrderListEntity> findAllOrderBythreereport(String start,String end,String status);
	
	@Query("SELECT o from OrderListEntity o WHERE (o.startDate BETWEEN ?1 AND ?2 OR o.endDate BETWEEN ?1 AND ?2) AND (o.isDelete='Active')")
	public List<OrderListEntity> monthlyListByDate(String start,String end);
	
	@Query("SELECT o from OrderListEntity o WHERE  o.status=?1 AND o.isDelete='Active' ")
	public List<OrderListEntity> findAllOrderByStatusReport(String status);
 
	
	@Query("SELECT o from OrderListEntity o WHERE o.startDate >=?1 AND  o.startDate<=?2 ")
	public OrderListEntity findCurrentWeekOrderList(String start,String end);
	
	//@Query("SELECT o from OrderListEntity o  WHERE o.startDate >=?1 AND  o.startDate<=?2 ")
	//public List<Object[]> findNextWeekRegisterOrderList(String start,String end);
	@Query("SELECT CASE WHEN COUNT(o) > 0 THEN 1 ELSE 0 END FROM OrderListEntity o WHERE o.startDate >= ?1 AND o.startDate <= ?2")
	public int findNextWeekRegisterOrderCount(String start, String end);
	
	@Query("SELECT o.id from OrderListEntity o WHERE o.startDate >=?1 AND  o.startDate<=?2 ")
	public Integer findNextWeekRegisterOrderListByID(String start,String end);
	 
	@Query(value = "SELECT DATE_ADD(CURRENT_DATE(), INTERVAL (6- WEEKDAY(CURRENT_DATE()) + 7) % 7 + 1 DAY)", nativeQuery = true)
	public Date getNextWeekMonday();
	
	@Query("SELECT o.endDate from OrderListEntity o WHERE o.startDate >=?1 AND  o.startDate<=?2 ")
	public String findNextWeekRegisterOrderListByEndDate(String start,String end);


	
}
