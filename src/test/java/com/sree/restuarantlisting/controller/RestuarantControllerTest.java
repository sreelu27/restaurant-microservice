package com.sree.restuarantlisting.controller;

import com.sree.restuarantlisting.dto.RestuarantDto;
import com.sree.restuarantlisting.service.RestuarantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestuarantControllerTest {

    @InjectMocks
    private RestuarantController restuarantController;

    @Mock
    private RestuarantService restuarantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); //in order for Mock and InjectMocks annotations to take effect, you need to call MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testfetchAllRestaurants(){

        // Mock the service behavior
        List<RestuarantDto> mockRestaurants = Arrays.asList(
                new RestuarantDto(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new RestuarantDto(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );

        when(restuarantService.findAllRestaurants()).thenReturn(mockRestaurants);

        // Call the controller method
        ResponseEntity<List<RestuarantDto>> response = restuarantController.fetchAllRestaurants();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurants, response.getBody());

        // Verify that the service method was called
        verify(restuarantService, times(1)).findAllRestaurants();

    }

    @Test
    public void testSaveRestaurant() {
        // Create a mock restaurant to be saved
        RestuarantDto mockRestaurant =  new RestuarantDto(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        // Mock the service behavior
        when(restuarantService.addRestaurantInDB(mockRestaurant)).thenReturn(mockRestaurant);

        // Call the controller method
        ResponseEntity<RestuarantDto> response = restuarantController.saveRestaurant(mockRestaurant);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRestaurant, response.getBody());

        // Verify that the service method was called
        verify(restuarantService, times(1)).addRestaurantInDB(mockRestaurant);
    }

    @Test
    public void testFindRestaurantById() {
        // Create a mock restaurant ID
        Integer mockRestaurantId = 1;

        // Create a mock restaurant to be returned by the service
        RestuarantDto mockRestaurant = new RestuarantDto(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        // Mock the service behavior
        when(restuarantService.fetchRestaurantById(mockRestaurantId)).thenReturn(new ResponseEntity<>(mockRestaurant, HttpStatus.OK));

        // Call the controller method
        ResponseEntity<RestuarantDto> response = restuarantController.findRestaurantById(mockRestaurantId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurant, response.getBody());

        // Verify that the service method was called
        verify(restuarantService, times(1)).fetchRestaurantById(mockRestaurantId);
    }
}
