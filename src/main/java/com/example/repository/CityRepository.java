package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	public Optional<City> findByName(String name);
	
	public boolean existsByName(String name);

}
