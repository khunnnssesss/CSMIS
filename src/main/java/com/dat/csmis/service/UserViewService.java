package com.dat.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.UserViewEntity;
import com.dat.csmis.repository.UserViewRepository;

@Service
	public class UserViewService {
	    private final UserViewRepository uvRepo;

	    @Autowired
	    public UserViewService(UserViewRepository uvRepo) {
	        this.uvRepo = uvRepo;
	    }

	    public List<UserViewEntity> findAll() {
	        return uvRepo.findAll();
	    }

		public List<UserViewEntity> getUserLunchInfo(String staffid){

			return uvRepo.findUserLuncInfo(staffid);
		}


		// public UserViewEntity getUE(String doorlogid){
		// 	UserViewEntity userUe = uvRepo.findUEdate(doorlogid);

		// 	return userUe;
		// }

		// public UserViewEntity getRNE(String doorlogid){
		// 	UserViewEntity userUe = uvRepo.findRNEdate(doorlogid);

		// 	return userUe;
		// }

		// public UserViewEntity getRE(String doorlogid){
		// 	UserViewEntity userUe = uvRepo.findREdate(doorlogid);

		// 	return userUe;
		// }

	}

