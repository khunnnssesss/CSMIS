package com.dat.csmis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.MenuEntity;
import com.dat.csmis.repository.MenuRepository;

@Service
public class MenuService {
	
	@Autowired
	MenuRepository repo;
	
	public MenuEntity select() {
		return repo.select();
	}
}
