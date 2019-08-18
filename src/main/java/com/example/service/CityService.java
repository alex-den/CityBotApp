package com.example.service;

import com.example.entity.City;

public interface CityService {

	public City getCityByName(String name);

	City getCityById(long id);

	public City addCity(City city);

	public void removeCity(long id);

	public City updateCity(City city);

	public String getInfoAboutCityByName(String name);

}
