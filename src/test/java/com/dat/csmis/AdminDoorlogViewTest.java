	package com.dat.csmis;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.mockito.ArgumentCaptor;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.Mockito;
	import org.mockito.MockitoAnnotations;
	import org.mockito.junit.jupiter.MockitoExtension;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
	import org.springframework.test.web.servlet.MockMvc;
	import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
	import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
	import org.springframework.test.web.servlet.setup.MockMvcBuilders;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.servlet.mvc.support.RedirectAttributes;

	import com.dat.csmis.controller.AdminController;
	import com.dat.csmis.entity.DoorLogEntity;
	import com.dat.csmis.entity.DoorLogViewEntity;
	import com.dat.csmis.entity.LunchViewEntity;
	import com.dat.csmis.repository.DoorlogViewRepository;
	import com.dat.csmis.repository.lunchViewRepository;

	import java.util.ArrayList;
	import java.util.List;
	import org.springframework.ui.Model;


	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.mockito.ArgumentMatchers.eq;
	import static org.mockito.Mockito.mock;
	import static org.mockito.Mockito.times;
	import static org.mockito.Mockito.verify;
	import static org.mockito.Mockito.when;
	import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
	
	

	@ExtendWith(MockitoExtension.class)
	public class AdminDoorlogViewTest {
		
		
		
	    @InjectMocks
	    private AdminController adminController;
	    
	    @Mock
	    private Model model;

	    @Mock 
	    DoorlogViewRepository dlRepo;
		
	    @Mock
	    lunchViewRepository lvRepo;
	    
	    @Mock
	    DoorLogEntity doorlogEntity;
	    
	    @Mock
	    DoorLogViewEntity doorlogviewEntity;
	    
	    @Mock
	    LunchViewEntity lunchviewEntity;
	    
	    @BeforeEach
	    void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	
		@Test
	    void rneList_shouldReturnAdminRegisterNoEatPage() throws Exception {
	        String selectedDate = "2023-05-24";
	        List<DoorLogViewEntity> rnelist = new ArrayList<>();
	        rnelist.add(new DoorLogViewEntity());
	        when(dlRepo.findRNE(selectedDate)).thenReturn(rnelist);
	        
	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();


	        mockMvc.perform(MockMvcRequestBuilders.get("/admin/rne")
	                .param("selectedDate", selectedDate))
	                .andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("rnelist"))
	                .andExpect(MockMvcResultMatchers.view().name("adminRegisterNoEat"));
	    }
		
		
		
		@Test
	    void ueList_shouldReturnAdminUnregisterEatPage() throws Exception {
	        String selectedDate = "2023-05-24";
	        List<DoorLogViewEntity> uelist = new ArrayList<>();
	        uelist.add(new DoorLogViewEntity());
	        when(dlRepo.findUE(selectedDate)).thenReturn(uelist);
	        
	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();


	        mockMvc.perform(MockMvcRequestBuilders.get("/admin/ue")
	                .param("selectedDate", selectedDate))
	                .andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("uelist"))
	                .andExpect(MockMvcResultMatchers.view().name("adminUnregisterEatList1"));
	    }
		
		
		
		
		
		@Test
	    void ueList_shouldReturnAdminTotalAccessListPage() throws Exception {
	        String selectedDate = "2023-05-24";
	        List<DoorLogViewEntity> totalList = new ArrayList<>();
			List<DoorLogViewEntity> relist = dlRepo.findRE(selectedDate);
			List<DoorLogViewEntity> uelist = dlRepo.findUE(selectedDate);
			for (DoorLogViewEntity re : relist) {
				totalList.add(re);
				}
				for (DoorLogViewEntity ue : uelist) {
				totalList.add(ue);
				}
	        totalList.add(new DoorLogViewEntity());
	        when(dlRepo.findRE(selectedDate)).thenReturn(relist);
	        when(dlRepo.findUE(selectedDate)).thenReturn(uelist);
	        
	        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();


	        mockMvc.perform(MockMvcRequestBuilders.get("/admin/replusue")
	                .param("selectedDate", selectedDate))
	                .andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("totalList"))
	                .andExpect(MockMvcResultMatchers.view().name("adminTotalAccessList"));
	    }
		
		
		


		
	
}
