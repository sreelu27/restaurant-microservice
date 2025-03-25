package com.sree.restuarantlisting.service;

import com.sree.restuarantlisting.dto.RestuarantDto;
import com.sree.restuarantlisting.entity.Restaurant;
import com.sree.restuarantlisting.mapper.RestuarantMapper;
import com.sree.restuarantlisting.repository.RestuarantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestuarantService {

    @Autowired
    private RestuarantRepository restaurantRepository;

    public List<RestuarantDto> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestuarantDto> restaurantDTOList = restaurants.stream().map(restaurant -> RestuarantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant)).collect(Collectors.toList());
        return restaurantDTOList;
    }

    public RestuarantDto addRestaurantInDB(RestuarantDto restaurantDTO) {
        Restaurant savedRestaurant =  restaurantRepository.save(RestuarantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));
        return RestuarantMapper.INSTANCE.mapRestaurantToRestaurantDTO(savedRestaurant);
    }

    public ResponseEntity<RestuarantDto> fetchRestaurantById(Integer id) {
        Optional<Restaurant> restaurant =  restaurantRepository.findById(id);
        if(restaurant.isPresent()){
            return new ResponseEntity<>(RestuarantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
