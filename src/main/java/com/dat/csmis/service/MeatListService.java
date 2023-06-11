package com.dat.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.csmis.entity.MeatList;
import com.dat.csmis.repository.MeatListRepo;

@Service
public class MeatListService {
    
    @Autowired private MeatListRepo meatrepo;

    public List<MeatList> getmeats(){

        return meatrepo.getallmeatlists();
    }
}
