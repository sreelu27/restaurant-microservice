package com.sree.restuarantlisting.repository;

import com.sree.restuarantlisting.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestuarantRepository extends JpaRepository<Restaurant,Integer> {
}
