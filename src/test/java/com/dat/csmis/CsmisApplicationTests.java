package com.dat.csmis;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dat.csmis.repository.HolidaysRepository;

@SpringBootTest
class CsmisApplicationTests {

	@Autowired HolidaysRepository testholi;
	

	@Test
	void contextLoads() {
	}


}
