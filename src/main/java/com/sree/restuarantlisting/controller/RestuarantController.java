package com.sree.restuarantlisting.controller;

import com.sree.restuarantlisting.dto.RestuarantDto;
import com.sree.restuarantlisting.service.RestuarantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resturant")
@CrossOrigin
public class RestuarantController {

    @Autowired
    private RestuarantService restuarantService;

    @GetMapping("/fetchAllRestaurants")
    public ResponseEntity<List<RestuarantDto>> fetchAllRestaurants(){
        List<RestuarantDto> allRestaurants = restuarantService.findAllRestaurants();
        return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<RestuarantDto> saveRestaurant(@RequestBody RestuarantDto restaurantDTO) {
        RestuarantDto restaurantAdded = restuarantService.addRestaurantInDB(restaurantDTO);
        return new ResponseEntity<>(restaurantAdded, HttpStatus.CREATED);
    }

    @GetMapping("fetchById/{id}")
    public ResponseEntity<RestuarantDto> findRestaurantById(@PathVariable Integer id) {
        return restuarantService.fetchRestaurantById(id);
    }


}
