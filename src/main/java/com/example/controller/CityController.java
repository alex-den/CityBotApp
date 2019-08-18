package com.example.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.City;
import com.example.service.CityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CityController {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
	}

	private final CityService cityService;

	@GetMapping(value = "/city", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<City> getCityByNAme(@RequestParam(value = "name") String cityName) {
		City city = cityService.getCityByName(cityName);
		return ResponseEntity.ok().body(city);
	}

	@PostMapping(value = "/city", produces = MediaType.APPLICATION_JSON_VALUE,
								  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<City> createCity(@Valid @RequestBody City newCity) {
		City city = cityService.addCity(newCity);
		return ResponseEntity.status(HttpStatus.CREATED).body(city);
	}

	@PutMapping(value = "/city/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
									  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<City> updateCity(@PathVariable(value = "id") Long id,
										   @Valid @RequestBody City updateCity) {
		City city = cityService.updateCity(updateCity);
		return ResponseEntity.ok().body(city);
	}

	@DeleteMapping(value = "/city/{id}")
	public ResponseEntity<Void> removeCity(@PathVariable(value = "id") Long id) {
		cityService.removeCity(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
