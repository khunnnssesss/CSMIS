package com.dat.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.FeedbackEntity;
import com.dat.csmis.repository.FeedbackRepository;
@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepo;
	public void save(FeedbackEntity entity) {
		
			feedbackRepo.save(entity);
		
		}
	public List<FeedbackEntity> getAllFeedback(){
		List<FeedbackEntity> list=feedbackRepo.findAll();
		return list;
	}
	
	public Optional<FeedbackEntity> findById(Integer id) {
		
		return feedbackRepo.findById(id);
	
	}
	public List<FeedbackEntity> getFeedbackByActive(){
		List<FeedbackEntity> list=feedbackRepo.getFeedbackByActive();
		return list;
	}
	 public int getFeedbackList() {
	    	return feedbackRepo.countByFeedbackList();
	    }
}
