package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.dat.csmis.controller.AdminController;
import com.dat.csmis.entity.FeedbackEntity;
import com.dat.csmis.service.FeedbackService;

@ExtendWith(MockitoExtension.class)
public class AdminFeedbackTest {

    @Mock
    private FeedbackService feedbackService;
    
    @Mock
    private FeedbackEntity feedbackEntity;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private Model model;

    @Test
    public void testDisplayFeedbackListPage() {
        // Arrange
        List<FeedbackEntity> feedbackList = Arrays.asList(new FeedbackEntity(), new FeedbackEntity());
        when(feedbackService.getFeedbackByActive()).thenReturn(feedbackList);

        // Act
        String viewName = adminController.displayFeedbackListPage(model);

        // Assert
        verify(feedbackService, times(1)).getFeedbackByActive();
        verify(model, times(1)).addAttribute(eq("list"), eq(feedbackList));
        assertEquals("adminFeedbackList", viewName);
    }

    @Test
    public void testDisplayDelete() {
        // Arrange
        Integer id = 1;
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        Optional<FeedbackEntity> feedbackOptional = Optional.of(feedbackEntity);
        when(feedbackService.findById(id)).thenReturn(feedbackOptional);

        // Act
        String viewName = adminController.displayDelete(id, model);

        // Assert
        verify(feedbackService, times(1)).findById(id);
        verify(feedbackService, times(1)).save(feedbackEntity);
        assertEquals("redirect:/admin/feedbackList", viewName);
    }
}
