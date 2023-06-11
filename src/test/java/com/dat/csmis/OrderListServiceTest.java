package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import com.dat.csmis.entity.OrderListEntity;
import com.dat.csmis.repository.OrderListRepository;
import com.dat.csmis.service.OrderListService;

@ExtendWith(MockitoExtension.class)
class OrderListServiceTest {

    @Mock
    private OrderListRepository orderListRepo;

    @InjectMocks
    private OrderListService orderListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSaveOrderListEntity() {
       
    	OrderListEntity order = new OrderListEntity();

        when(orderListRepo.save(order)).thenReturn(order);

        OrderListEntity savedOrder = orderListService.save(order);

        verify(orderListRepo).save(order);

        assertEquals(order, savedOrder);
    }

    @Test
    void getFindAll_shouldReturnAllOrderListEntities() {

    	List<OrderListEntity> orderList = new ArrayList<>();

        when(orderListRepo.findAll()).thenReturn(orderList);

        List<OrderListEntity> resultList = orderListService.getFindAll();

        verify(orderListRepo).findAll();

        assertEquals(orderList, resultList);
    }

    @Test
    void getFindOne_shouldReturnOrderListEntityById() {
        
    	Integer id = 1;

        OrderListEntity order = new OrderListEntity();

        when(orderListRepo.findById(id)).thenReturn(Optional.of(order));

        Optional<OrderListEntity> result = orderListService.getFindOne(id);

        verify(orderListRepo).findById(id);

        assertEquals(Optional.of(order), result);
    }
    
    @Test
    void findPagination_shouldReturnPageOfOrderListEntities() {
       
    	int pageNo = 1;
        int pageSize = 10;
        String sortField = "fieldName";
        String sortDirection = "asc";
        String start = "2023-01-01";
        String end = "2023-12-31";
        String status = "status";

        List<OrderListEntity> orderList = new ArrayList<>();

        Page<OrderListEntity> page = new PageImpl<>(orderList);

        when(orderListRepo.findAllOrderBythree(any(Pageable.class), eq(start), eq(end), eq(status))).thenReturn(page);

        Page<OrderListEntity> result = orderListService.findPagination(pageNo, pageSize, sortField, sortDirection, start, end, status);

        verify(orderListRepo).findAllOrderBythree(any(Pageable.class), eq(start), eq(end), eq(status));

        assertEquals(page, result);
    }

    @Test
    void monthlyDateList_shouldReturnListOfOrderListEntities() {
        String start = "2023-01-01";
        String end = "2023-12-31";

        List<OrderListEntity> orderList = new ArrayList<>();

        when(orderListRepo.monthlyListByDate(start, end)).thenReturn(orderList);

        List<OrderListEntity> result = orderListService.monthlyDateList(start, end);

        verify(orderListRepo).monthlyListByDate(start, end);

        assertEquals(orderList, result);
    }
    
    

    @Test
    void findPaginationCost_shouldReturnPageOfOrderListEntities() {
        int pageNo = 1;
        int pageSize = 10;
        String sortField = "fieldName";
        String sortDirection = "asc";
        String start = "2023-01-01";
        String end = "2023-12-31";

        List<OrderListEntity> orderList = new ArrayList<>();

        Page<OrderListEntity> page = new PageImpl<>(orderList);

        when(orderListRepo.findAllO(any(Pageable.class), eq(start), eq(end))).thenReturn(page);

        Page<OrderListEntity> result = orderListService.findPaginationCost(pageNo, pageSize, sortField, sortDirection, start, end);

        verify(orderListRepo).findAllO(any(Pageable.class), eq(start), eq(end));

        assertEquals(page, result);
    }

    @Test
    void findOrderListByStatusreport_shouldReturnListOfOrderListEntities() {
        String status = "status";

        List<OrderListEntity> orderList = new ArrayList<>();

        when(orderListRepo.findAllOrderByStatusReport(status)).thenReturn(orderList);

        List<OrderListEntity> result = orderListService.findOrderListByStatusreport(status);

        verify(orderListRepo).findAllOrderByStatusReport(status);

        assertEquals(orderList, result);
    }
    
    @Test
    void findOrderListByThreereport_shouldReturnListOfOrderListEntities() {
        String start = "2023-01-01";
        String end = "2023-12-31";
        String status = "status";

        List<OrderListEntity> orderList = new ArrayList<>();

        when(orderListRepo.findAllOrderBythreereport(start, end, status)).thenReturn(orderList);

        List<OrderListEntity> result = orderListService.findOrderListByThreereport(start, end, status);

        verify(orderListRepo).findAllOrderBythreereport(start, end, status);

        assertEquals(orderList, result);
    }

    @Test
    void getCurrentWeekOrderList_shouldReturnOrderListEntity() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = dateFormat.format(calendar.getTime());

        OrderListEntity order = new OrderListEntity();

        when(orderListRepo.findCurrentWeekOrderList(startDate, endDate)).thenReturn(order);

        OrderListEntity result = orderListService.getCurrentWeekOrderList();

        verify(orderListRepo).findCurrentWeekOrderList(startDate, endDate);

        assertEquals(order, result);
    }
    
    @Test
    void getNextWeekRegisterOrderCount_shouldReturnCountOfOrders() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = dateFormat.format(calendar.getTime());
        int orderCount = 10;

        when(orderListRepo.findNextWeekRegisterOrderCount(startDate, endDate)).thenReturn(orderCount);

        int result = orderListService.getNextWeekRegisterOrderCount();

        verify(orderListRepo).findNextWeekRegisterOrderCount(startDate, endDate);

        assertEquals(orderCount, result);
    }
    
    @Test
    void getNextWeekRegisterOrderByID_shouldReturnOrderListId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = dateFormat.format(calendar.getTime());
        Integer orderListId = 1;

        when(orderListRepo.findNextWeekRegisterOrderListByID(startDate, endDate)).thenReturn(orderListId);

        Integer result = orderListService.getNextWeekRegisterOrderByID();

        verify(orderListRepo).findNextWeekRegisterOrderListByID(startDate, endDate);

        assertEquals(orderListId, result);
    }

    @Test
    void getNextWeekRegisterOrderByEndDate_shouldReturnOrderEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = dateFormat.format(calendar.getTime());
        String orderEndDate = "2023-01-01";

        when(orderListRepo.findNextWeekRegisterOrderListByEndDate(startDate, endDate)).thenReturn(orderEndDate);

        String result = orderListService.getNextWeekRegisterOrderByEndDate();

        verify(orderListRepo).findNextWeekRegisterOrderListByEndDate(startDate, endDate);

        assertEquals(orderEndDate, result);
        
    }
    
    
}