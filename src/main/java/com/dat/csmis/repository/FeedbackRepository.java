package com.dat.csmis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.FeedbackEntity;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity,Integer >{
	@Query("select f from FeedbackEntity f")
	List<FeedbackEntity> getFeedbackByActive();
	
	 @Query("SELECT COUNT(f) FROM FeedbackEntity f")
	    int countByFeedbackList();
}
