package com.dat.csmis;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dat.csmis.entity.FeedbackEntity;
import com.dat.csmis.repository.FeedbackRepository;
import com.dat.csmis.service.FeedbackService;

public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setId(1);
        feedback.setFeedback("Test feedback");

        feedbackService.save(feedback);

        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    public void testGetAllFeedback() {
        List<FeedbackEntity> feedbackList = new ArrayList<>();
        FeedbackEntity feedback1 = new FeedbackEntity();
        feedback1.setId(1);
        feedback1.setFeedback("Test feedback 1");
        feedbackList.add(feedback1);

        FeedbackEntity feedback2 = new FeedbackEntity();
        feedback2.setId(2);
        feedback2.setFeedback("Test feedback 2");
        feedbackList.add(feedback2);

        when(feedbackRepository.findAll()).thenReturn(feedbackList);

        List<FeedbackEntity> result = feedbackService.getAllFeedback();

        verify(feedbackRepository, times(1)).findAll();

        assertEquals(feedbackList, result);
    }

    @Test
    public void testFindById() {
        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setId(1);
        feedback.setFeedback("Test feedback");
        Optional<FeedbackEntity> optionalFeedback = Optional.of(feedback);

        when(feedbackRepository.findById(1)).thenReturn(optionalFeedback);

        Optional<FeedbackEntity> result = feedbackService.findById(1);

        verify(feedbackRepository, times(1)).findById(1);

        assertEquals(optionalFeedback, result);
    }

    @Test
    public void testGetFeedbackByActive() {
        List<FeedbackEntity> feedbackList = new ArrayList<>();
        FeedbackEntity feedback1 = new FeedbackEntity();
        feedback1.setId(1);
        feedback1.setFeedback("Test feedback 1");
        feedbackList.add(feedback1);

        FeedbackEntity feedback2 = new FeedbackEntity();
        feedback2.setId(2);
        feedback2.setFeedback("Test feedback 2");
        feedbackList.add(feedback2);

        when(feedbackRepository.getFeedbackByActive()).thenReturn(feedbackList);

        List<FeedbackEntity> result = feedbackService.getFeedbackByActive();

        verify(feedbackRepository, times(1)).getFeedbackByActive();

        assertEquals(feedbackList, result);
    }

    @Test
    public void testGetFeedbackList() {
        int expectedCount = 5;

        when(feedbackRepository.countByFeedbackList()).thenReturn(expectedCount);

        int result = feedbackService.getFeedbackList();

        verify(feedbackRepository, times(1)).countByFeedbackList();

        assertEquals(expectedCount, result);
    }
}
