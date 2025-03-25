package com.sree.restuarantlisting.mapper;

import com.sree.restuarantlisting.dto.RestuarantDto;
import com.sree.restuarantlisting.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestuarantMapper {

    RestuarantMapper INSTANCE = Mappers.getMapper(RestuarantMapper.class);

    Restaurant mapRestaurantDTOToRestaurant(RestuarantDto restaurantDTO);

    RestuarantDto mapRestaurantToRestaurantDTO(Restaurant restaurant);

}
